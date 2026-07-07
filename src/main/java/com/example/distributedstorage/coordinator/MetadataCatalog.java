package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.ChecksumFiles;
import com.example.distributedstorage.common.NodeAddress;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class MetadataCatalog {
    private final Map<String, ConcurrentSkipListMap<Integer, List<NodeAddress>>> files = new ConcurrentHashMap<>();
    private final Path snapshotFile;

    public MetadataCatalog() {
        this(null);
    }

    public MetadataCatalog(Path snapshotFile) {
        this.snapshotFile = snapshotFile;
        loadFromDisk();
    }

    public synchronized void put(String fileName, int chunkIndex, List<NodeAddress> replicas) {
        files.computeIfAbsent(fileName, ignored -> new ConcurrentSkipListMap<>()).put(chunkIndex, List.copyOf(replicas));
        saveToDisk();
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
        return files.keySet().stream()
                .filter(fileName -> !ChecksumFiles.isChecksumFile(fileName))
                .sorted()
                .toList();
    }

    private void loadFromDisk() {
        if (snapshotFile == null || !Files.exists(snapshotFile)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(snapshotFile, StandardCharsets.UTF_8)) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|", -1);
                if (parts.length != 3) {
                    throw new IllegalStateException("Bad metadata line: " + line);
                }
                String fileName = new String(Base64.getDecoder().decode(parts[0]), StandardCharsets.UTF_8);
                int chunkIndex = Integer.parseInt(parts[1]);
                List<NodeAddress> replicas = parts[2].isBlank()
                        ? List.of()
                        : java.util.Arrays.stream(parts[2].split(","))
                        .map(NodeAddress::parse)
                        .toList();
                files.computeIfAbsent(fileName, ignored -> new ConcurrentSkipListMap<>()).put(chunkIndex, replicas);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read metadata snapshot from " + snapshotFile, exception);
        }
    }

    private void saveToDisk() {
        if (snapshotFile == null) {
            return;
        }
        StringBuilder content = new StringBuilder();
        for (ChunkLocation location : allChunks()) {
            content.append(Base64.getEncoder().encodeToString(location.fileName().getBytes(StandardCharsets.UTF_8)))
                    .append('|')
                    .append(location.chunkIndex())
                    .append('|')
                    .append(location.replicas().stream().map(NodeAddress::toString).collect(java.util.stream.Collectors.joining(",")))
                    .append(System.lineSeparator());
        }
        try {
            Path parent = snapshotFile.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Path tempFile = snapshotFile.resolveSibling(snapshotFile.getFileName() + ".tmp");
            Files.writeString(tempFile, content.toString(), StandardCharsets.UTF_8);
            try {
                Files.move(tempFile, snapshotFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(tempFile, snapshotFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not write metadata snapshot to " + snapshotFile, exception);
        }
    }
}
