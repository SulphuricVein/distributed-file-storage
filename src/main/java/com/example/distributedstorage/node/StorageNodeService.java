package com.example.distributedstorage.node;

import com.example.distributedstorage.proto.Chunk;
import com.example.distributedstorage.proto.ChunkRequest;
import com.example.distributedstorage.proto.ChunkResponse;
import com.example.distributedstorage.proto.HealthRequest;
import com.example.distributedstorage.proto.HealthResponse;
import com.example.distributedstorage.proto.StorageNodeGrpc;
import com.example.distributedstorage.proto.StoreChunkResponse;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public final class StorageNodeService extends StorageNodeGrpc.StorageNodeImplBase {
    private final Path dataDirectory;
    private final String nodeId;

    public StorageNodeService(Path dataDirectory, String nodeId) {
        this.dataDirectory = dataDirectory;
        this.nodeId = nodeId;
    }

    @Override
    public void storeChunk(Chunk request, StreamObserver<StoreChunkResponse> responseObserver) {
        if (request.getFileName().isBlank() || request.getChunkIndex() < 0) {
            responseObserver.onNext(StoreChunkResponse.newBuilder().setSuccess(false).setMessage("file name and chunk index are required").build());
            responseObserver.onCompleted();
            return;
        }
        try {
            Files.createDirectories(dataDirectory);
            Path target = chunkPath(request.getFileName(), request.getChunkIndex());
            Path temporary = Files.createTempFile(dataDirectory, "chunk-", ".tmp");
            Files.write(temporary, request.getData().toByteArray());
            Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            responseObserver.onNext(StoreChunkResponse.newBuilder().setSuccess(true).setMessage("stored on " + nodeId).build());
            responseObserver.onCompleted();
        } catch (IOException exception) {
            responseObserver.onNext(StoreChunkResponse.newBuilder().setSuccess(false).setMessage(exception.getMessage()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getChunk(ChunkRequest request, StreamObserver<ChunkResponse> responseObserver) {
        try {
            Path source = chunkPath(request.getFileName(), request.getChunkIndex());
            if (!Files.exists(source)) {
                responseObserver.onNext(ChunkResponse.newBuilder().setFound(false).setMessage("chunk not found").build());
            } else {
                responseObserver.onNext(ChunkResponse.newBuilder().setFound(true).setData(com.google.protobuf.ByteString.copyFrom(Files.readAllBytes(source))).build());
            }
            responseObserver.onCompleted();
        } catch (IOException exception) {
            responseObserver.onNext(ChunkResponse.newBuilder().setFound(false).setMessage(exception.getMessage()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void health(HealthRequest request, StreamObserver<HealthResponse> responseObserver) {
        responseObserver.onNext(HealthResponse.newBuilder().setHealthy(true).setNodeId(nodeId).build());
        responseObserver.onCompleted();
    }

    private Path chunkPath(String fileName, int chunkIndex) {
        String fileId = Base64.getUrlEncoder().withoutPadding().encodeToString(fileName.getBytes(StandardCharsets.UTF_8));
        return dataDirectory.resolve(fileId + "-" + chunkIndex + ".chunk");
    }
}

