package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.NodeAddress;
import com.example.distributedstorage.common.NodeClient;
import com.example.distributedstorage.proto.Chunk;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class RecoveryService implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(RecoveryService.class);
    private final List<NodeAddress> configuredNodes;
    private final int replicationFactor;
    private final MetadataCatalog catalog;

    public RecoveryService(List<NodeAddress> configuredNodes, int replicationFactor, MetadataCatalog catalog) {
        this.configuredNodes = List.copyOf(configuredNodes);
        this.replicationFactor = replicationFactor;
        this.catalog = catalog;
    }

    @Override
    public void run() {
        List<NodeAddress> liveNodes = configuredNodes.stream().filter(this::isHealthy).toList();
        for (ChunkLocation location : catalog.allChunks()) {
            recover(location, liveNodes);
        }
    }

    private void recover(ChunkLocation location, List<NodeAddress> liveNodes) {
        List<NodeAddress> healthyReplicas = location.replicas().stream().filter(this::isHealthy).toList();
        int desiredReplicas = Math.min(replicationFactor, liveNodes.size());
        if (healthyReplicas.size() >= desiredReplicas) {
            return;
        }
        if (healthyReplicas.isEmpty()) {
            LOG.warn("Cannot recover {}/{}: no surviving copy", location.fileName(), location.chunkIndex());
            return;
        }
        byte[] data = read(location, healthyReplicas.get(0));
        if (data == null) {
            return;
        }
        List<NodeAddress> recovered = new ArrayList<>(healthyReplicas);
        for (NodeAddress candidate : liveNodes) {
            if (recovered.size() >= desiredReplicas) {
                break;
            }
            if (recovered.contains(candidate)) {
                continue;
            }
            if (write(location, candidate, data)) {
                recovered.add(candidate);
                LOG.info("Recovered {}/{} onto {}", location.fileName(), location.chunkIndex(), candidate);
            }
        }
        catalog.put(location.fileName(), location.chunkIndex(), recovered);
    }

    private boolean isHealthy(NodeAddress node) {
        try (NodeClient client = new NodeClient(node)) {
            return client.isHealthy();
        }
    }

    private byte[] read(ChunkLocation location, NodeAddress node) {
        try (NodeClient client = new NodeClient(node)) {
            var response = client.get(location.fileName(), location.chunkIndex());
            return response.getFound() ? response.getData().toByteArray() : null;
        } catch (RuntimeException exception) {
            LOG.warn("Could not read {}/{} from {}", location.fileName(), location.chunkIndex(), node, exception);
            return null;
        }
    }

    private boolean write(ChunkLocation location, NodeAddress node, byte[] data) {
        try (NodeClient client = new NodeClient(node)) {
            return client.store(Chunk.newBuilder()
                    .setFileName(location.fileName())
                    .setChunkIndex(location.chunkIndex())
                    .setData(ByteString.copyFrom(data))
                    .build()).getSuccess();
        } catch (RuntimeException exception) {
            LOG.warn("Could not recover {}/{} to {}", location.fileName(), location.chunkIndex(), node, exception);
            return false;
        }
    }
}

