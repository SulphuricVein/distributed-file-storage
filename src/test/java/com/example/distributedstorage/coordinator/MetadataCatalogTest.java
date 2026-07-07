package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.ChecksumFiles;
import com.example.distributedstorage.common.NodeAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.List;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetadataCatalogTest {
    @TempDir
    Path tempDir;

    @Test
    void keepsChunksInIndexOrderAndReplacesReplicaLocations() {
        MetadataCatalog catalog = new MetadataCatalog();
        NodeAddress first = new NodeAddress("node-1", 50061);
        NodeAddress second = new NodeAddress("node-2", 50062);

        catalog.put("report.pdf", 2, List.of(first));
        catalog.put("report.pdf", 0, List.of(first, second));
        catalog.put("report.pdf", 2, List.of(second));

        List<ChunkLocation> chunks = catalog.chunks("report.pdf");
        assertEquals(List.of(0, 2), chunks.stream().map(ChunkLocation::chunkIndex).toList());
        assertEquals(List.of(second), chunks.get(1).replicas());
        assertEquals(List.of("report.pdf"), catalog.fileNames());
    }

    @Test
    void reloadsSavedMetadataFromDisk() {
        Path snapshot = tempDir.resolve("catalog.tsv");
        NodeAddress first = new NodeAddress("node-1", 50061);
        NodeAddress second = new NodeAddress("node-2", 50062);

        MetadataCatalog original = new MetadataCatalog(snapshot);
        original.put("docs/report.pdf", 1, List.of(first));
        original.put("docs/report.pdf", 0, List.of(first, second));
        original.put("notes.txt", 0, List.of(second));

        MetadataCatalog reloaded = new MetadataCatalog(snapshot);

        assertEquals(List.of("docs/report.pdf", "notes.txt"), reloaded.fileNames());
        assertEquals(List.of(0, 1), reloaded.chunks("docs/report.pdf").stream().map(ChunkLocation::chunkIndex).toList());
        assertEquals(List.of(first, second), reloaded.chunks("docs/report.pdf").get(0).replicas());
        assertEquals(List.of(second), reloaded.chunks("notes.txt").get(0).replicas());
    }

    @Test
    void hidesInternalChecksumFilesFromNormalListings() {
        MetadataCatalog catalog = new MetadataCatalog();
        NodeAddress node = new NodeAddress("node-1", 50061);

        catalog.put("report.pdf", 0, List.of(node));
        catalog.put(ChecksumFiles.forFile("report.pdf"), 0, List.of(node));

        assertEquals(List.of("report.pdf"), catalog.fileNames());
        assertEquals(1, catalog.chunks(ChecksumFiles.forFile("report.pdf")).size());
    }
}
