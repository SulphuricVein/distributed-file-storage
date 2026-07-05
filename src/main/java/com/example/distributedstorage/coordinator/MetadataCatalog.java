package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.NodeAddress;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class MetadataCatalog {
    private final Map<String, ConcurrentSkipListMap<Integer, List<NodeAddress>>> files = new ConcurrentHashMap<>();

    public void put(String fileName, int chunkIndex, List<NodeAddress> replicas) {
        files.computeIfAbsent(fileName, ignored -> new ConcurrentSkipListMap<>()).put(chunkIndex, List.copyOf(replicas));
    }

    public List<ChunkLocation> chunks(String fileName) {
        Map<Integer, List<NodeAddress>> file = files.get(fileName);
        if (file == null) {
            return List.of();
        }
        return file.entrySet().stream()
                .map(entry -> new ChunkLocation(fileName, entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<ChunkLocation> allChunks() {
        List<ChunkLocation> result = new ArrayList<>();
        files.forEach((fileName, chunks) -> chunks.forEach((index, replicas) -> result.add(new ChunkLocation(fileName, index, replicas))));
        result.sort(Comparator.comparing(ChunkLocation::fileName).thenComparingInt(ChunkLocation::chunkIndex));
        return result;
    }

    public List<String> fileNames() {
        return files.keySet().stream().sorted().toList();
    }
}

