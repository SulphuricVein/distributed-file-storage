package com.example.distributedstorage.client;

import com.example.distributedstorage.common.NodeAddress;
import com.example.distributedstorage.proto.Chunk;
import com.example.distributedstorage.proto.CoordinatorGrpc;
import com.example.distributedstorage.proto.DownloadRequest;
import com.example.distributedstorage.proto.ListFilesRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

public final class FileClient {
    private static final int CHUNK_SIZE_BYTES = 1024 * 1024;

    private FileClient() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            usage();
            return;
        }
        String command = args[0];
        String endpoint = switch (command) {
            case "upload", "download" -> args.length == 4 ? args[3] : "localhost:50051";
            case "list" -> args.length == 2 ? args[1] : "localhost:50051";
            default -> "localhost:50051";
        };
        NodeAddress address = NodeAddress.parse(endpoint);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address.host(), address.port()).usePlaintext().build();
        try {
            CoordinatorGrpc.CoordinatorBlockingStub coordinator = CoordinatorGrpc.newBlockingStub(channel);
            switch (command) {
                case "upload" -> upload(coordinator, args);
                case "download" -> download(coordinator, args);
                case "list" -> list(coordinator);
                default -> usage();
            }
        } finally {
            channel.shutdownNow();
        }
    }

    private static void upload(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            usage();
            return;
        }
        Path source = Path.of(args[1]);
        String remoteName = args[2];
        int chunkIndex = 0;
        try (InputStream input = Files.newInputStream(source)) {
            byte[] buffer = new byte[CHUNK_SIZE_BYTES];
            int bytesRead;
            do {
                bytesRead = input.read(buffer);
                if (bytesRead < 0) {
                    break;
                }
                byte[] chunkData = java.util.Arrays.copyOf(buffer, bytesRead);
                var response = coordinator.storeChunk(Chunk.newBuilder()
                        .setFileName(remoteName)
                        .setChunkIndex(chunkIndex++)
                        .setData(com.google.protobuf.ByteString.copyFrom(chunkData))
                        .build());
                if (!response.getSuccess()) {
                    throw new IllegalStateException("Upload stopped: " + response.getMessage());
                }
            } while (bytesRead == CHUNK_SIZE_BYTES);
        }
        if (chunkIndex == 0) {
            var response = coordinator.storeChunk(Chunk.newBuilder().setFileName(remoteName).setChunkIndex(0).build());
            if (!response.getSuccess()) {
                throw new IllegalStateException("Upload stopped: " + response.getMessage());
            }
        }
        System.out.printf("Uploaded %s in %d chunk(s)%n", source, Math.max(chunkIndex, 1));
    }

    private static void download(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            usage();
            return;
        }
        Path destination = Path.of(args[2]);
        Path parent = destination.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temporary = destination.resolveSibling(destination.getFileName() + ".part");
        Iterator<Chunk> chunks = coordinator.downloadFile(DownloadRequest.newBuilder().setFileName(args[1]).build());
        try (OutputStream output = Files.newOutputStream(temporary)) {
            while (chunks.hasNext()) {
                output.write(chunks.next().getData().toByteArray());
            }
        }
        Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        System.out.println("Downloaded to " + destination.toAbsolutePath());
    }

    private static void list(CoordinatorGrpc.CoordinatorBlockingStub coordinator) {
        coordinator.listFiles(ListFilesRequest.getDefaultInstance()).getFileNamesList().forEach(System.out::println);
    }

    private static void usage() {
        System.out.println("Usage:\n  upload <local-file> <remote-name> [coordinator-host:port]\n  download <remote-name> <local-file> [coordinator-host:port]\n  list [coordinator-host:port]");
    }
}

