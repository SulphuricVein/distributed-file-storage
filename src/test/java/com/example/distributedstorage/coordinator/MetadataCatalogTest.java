package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.NodeAddress;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetadataCatalogTest {
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
}
