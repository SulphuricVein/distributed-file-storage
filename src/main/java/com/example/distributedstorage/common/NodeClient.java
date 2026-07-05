package com.example.distributedstorage.common;

import com.example.distributedstorage.proto.Chunk;
import com.example.distributedstorage.proto.ChunkRequest;
import com.example.distributedstorage.proto.ChunkResponse;
import com.example.distributedstorage.proto.HealthRequest;
import com.example.distributedstorage.proto.StorageNodeGrpc;
import com.example.distributedstorage.proto.StoreChunkResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public final class NodeClient implements AutoCloseable {
    private final ManagedChannel channel;
    private final StorageNodeGrpc.StorageNodeBlockingStub stub;

    public NodeClient(NodeAddress address) {
        channel = ManagedChannelBuilder.forAddress(address.host(), address.port()).usePlaintext().build();
        stub = StorageNodeGrpc.newBlockingStub(channel).withDeadlineAfter(2, TimeUnit.SECONDS);
    }

    public boolean isHealthy() {
        try {
            return stub.health(HealthRequest.getDefaultInstance()).getHealthy();
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public StoreChunkResponse store(Chunk chunk) {
        return stub.storeChunk(chunk);
    }

    public ChunkResponse get(String fileName, int chunkIndex) {
        return stub.getChunk(ChunkRequest.newBuilder().setFileName(fileName).setChunkIndex(chunkIndex).build());
    }

    @Override
    public void close() {
        channel.shutdownNow();
    }
}

