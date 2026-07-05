package com.example.distributedstorage.node;

import com.example.distributedstorage.common.Arguments;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.nio.file.Path;
import java.util.Map;

public final class StorageNodeMain {
    private StorageNodeMain() {
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> options = Arguments.parse(args);
        int port = Integer.parseInt(options.getOrDefault("port", "50061"));
        Path dataDirectory = Path.of(options.getOrDefault("data-dir", "data/node-" + port));
        Server server = ServerBuilder.forPort(port)
                .addService(new StorageNodeService(dataDirectory, "node-" + port))
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        System.out.printf("Storage node listening on %d; data=%s%n", port, dataDirectory.toAbsolutePath());
        server.awaitTermination();
    }
}

