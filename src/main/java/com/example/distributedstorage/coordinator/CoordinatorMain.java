package com.example.distributedstorage.coordinator;

import com.example.distributedstorage.common.Arguments;
import com.example.distributedstorage.common.NodeAddress;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class CoordinatorMain {
    private CoordinatorMain() {
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> options = Arguments.parse(args);
        int port = Integer.parseInt(options.getOrDefault("port", "50051"));
        int replicas = Integer.parseInt(options.getOrDefault("replicas", "2"));
        long recoverySeconds = Long.parseLong(options.getOrDefault("recovery-seconds", "5"));
        List<NodeAddress> nodes = Arrays.stream(options.getOrDefault("nodes", "localhost:50061,localhost:50062,localhost:50063").split(","))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(NodeAddress::parse)
                .toList();
        if (nodes.isEmpty() || replicas < 1 || replicas > nodes.size()) {
            throw new IllegalArgumentException("Provide at least one node and a replica count between 1 and node count");
        }

        MetadataCatalog catalog = new MetadataCatalog();
        ScheduledExecutorService recoveryExecutor = Executors.newSingleThreadScheduledExecutor();
        recoveryExecutor.scheduleWithFixedDelay(new RecoveryService(nodes, replicas, catalog), recoverySeconds, recoverySeconds, TimeUnit.SECONDS);

        Server server = ServerBuilder.forPort(port)
                .addService(new CoordinatorService(nodes, replicas, catalog))
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            recoveryExecutor.shutdownNow();
            server.shutdown();
        }));
        System.out.printf("Coordinator listening on %d; replicas=%d; nodes=%s%n", port, replicas, nodes);
        server.awaitTermination();
    }
}

