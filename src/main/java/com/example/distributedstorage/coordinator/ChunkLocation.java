package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.NodeAddress;

import java.util.List;

public record ChunkLocation(String fileName, int chunkIndex, List<NodeAddress> replicas) {
    public ChunkLocation {
        replicas = List.copyOf(replicas);
    }
}

