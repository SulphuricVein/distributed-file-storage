package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.NodeAddress;
import com.example.distributedstorage.common.NodeClient;
import com.example.distributedstorage.proto.Chunk;
import com.example.distributedstorage.proto.CoordinatorGrpc;
import com.example.distributedstorage.proto.DownloadRequest;
import com.example.distributedstorage.proto.ListFilesRequest;
import com.example.distributedstorage.proto.ListFilesResponse;
import com.example.distributedstorage.proto.StoreChunkResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public final class CoordinatorService extends CoordinatorGrpc.CoordinatorImplBase {
    private final List<NodeAddress> configuredNodes;
    private final int replicationFactor;
    private final MetadataCatalog catalog;

    public CoordinatorService(List<NodeAddress> configuredNodes, int replicationFactor, MetadataCatalog catalog) {
        this.configuredNodes = List.copyOf(configuredNodes);
        this.replicationFactor = replicationFactor;
        this.catalog = catalog;
    }

    @Override
    public void storeChunk(Chunk request, StreamObserver<StoreChunkResponse> responseObserver) {
        if (request.getFileName().isBlank() || request.getChunkIndex() < 0) {
            responseObserver.onNext(StoreChunkResponse.newBuilder().setSuccess(false).setMessage("file name and non-negative chunk index are required").build());
            responseObserver.onCompleted();
            return;
        }
        List<NodeAddress> storedOn = new ArrayList<>();
        for (NodeAddress node : configuredNodes) {
            if (storedOn.size() == replicationFactor) {
                break;
            }
            try (NodeClient client = new NodeClient(node)) {
                if (client.isHealthy() && client.store(request).getSuccess()) {
                    storedOn.add(node);
                }
            } catch (RuntimeException ignored) {
                // This node is unavailable; another replica can still satisfy the write.
            }
        }
        if (!storedOn.isEmpty()) {
            catalog.put(request.getFileName(), request.getChunkIndex(), storedOn);
        }
        boolean replicated = storedOn.size() == replicationFactor;
        responseObserver.onNext(StoreChunkResponse.newBuilder()
                .setSuccess(replicated)
                .setMessage("stored on " + storedOn.size() + "/" + replicationFactor + " replicas")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void downloadFile(DownloadRequest request, StreamObserver<Chunk> responseObserver) {
        List<ChunkLocation> chunks = catalog.chunks(request.getFileName());
        if (chunks.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("file not found").asRuntimeException());
            return;
        }
        try {
            for (ChunkLocation location : chunks) {
                Chunk chunk = readFromAnyReplica(location);
                if (chunk == null) {
                    throw Status.UNAVAILABLE.withDescription("no healthy replica for chunk " + location.chunkIndex()).asRuntimeException();
                }
                responseObserver.onNext(chunk);
            }
            responseObserver.onCompleted();
        } catch (RuntimeException exception) {
            responseObserver.onError(exception);
        }
    }

    @Override
    public void listFiles(ListFilesRequest request, StreamObserver<ListFilesResponse> responseObserver) {
        responseObserver.onNext(ListFilesResponse.newBuilder().addAllFileNames(catalog.fileNames()).build());
        responseObserver.onCompleted();
    }

    private Chunk readFromAnyReplica(ChunkLocation location) {
        for (NodeAddress node : location.replicas()) {
            try (NodeClient client = new NodeClient(node)) {
                if (!client.isHealthy()) {
                    continue;
                }
                var response = client.get(location.fileName(), location.chunkIndex());
                if (response.getFound()) {
                    return Chunk.newBuilder()
                            .setFileName(location.fileName())
                            .setChunkIndex(location.chunkIndex())
                            .setData(response.getData())
                            .build();
                }
            } catch (RuntimeException ignored) {
                // Try the next replica.
            }
        }
        return null;
    }
}

