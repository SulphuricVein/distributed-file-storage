package com.example.distributedstorage.client;

import com.example.distributedstorage.common.ChecksumFiles;
import com.example.distributedstorage.common.ChecksumUtil;
import com.example.distributedstorage.common.NodeAddress;
import com.example.distributedstorage.proto.Chunk;
import com.example.distributedstorage.proto.CoordinatorGrpc;
import com.example.distributedstorage.proto.DownloadRequest;
import com.example.distributedstorage.proto.ListFilesRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
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
            case "upload", "download", "verify" -> args.length == 4 ? args[3] : "localhost:50051";
            case "checksum" -> args.length == 3 ? args[2] : "localhost:50051";
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
                case "verify" -> verify(coordinator, args);
                case "checksum" -> checksum(coordinator, args);
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
        MessageDigest digest = ChecksumUtil.newSha256();
        try (InputStream input = Files.newInputStream(source)) {
            byte[] buffer = new byte[CHUNK_SIZE_BYTES];
            int bytesRead;
            do {
                bytesRead = input.read(buffer);
                if (bytesRead < 0) {
                    break;
                }
                byte[] chunkData = java.util.Arrays.copyOf(buffer, bytesRead);
                digest.update(chunkData);
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
        storeChecksum(coordinator, remoteName, ChecksumUtil.hex(digest.digest()));
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
        MessageDigest digest = ChecksumUtil.newSha256();
        try (OutputStream output = Files.newOutputStream(temporary)) {
            while (chunks.hasNext()) {
                byte[] data = chunks.next().getData().toByteArray();
                digest.update(data);
                output.write(data);
            }
            String expectedChecksum = readChecksum(coordinator, args[1]);
            if (expectedChecksum != null && !expectedChecksum.equals(ChecksumUtil.hex(digest.digest()))) {
                throw new IllegalStateException("Download failed: checksum mismatch for " + args[1]);
            }
        } catch (Exception exception) {
            Files.deleteIfExists(temporary);
            throw exception;
        }
        try {
            Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ignored) {
            Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Downloaded to " + destination.toAbsolutePath());
    }

    private static void list(CoordinatorGrpc.CoordinatorBlockingStub coordinator) {
        coordinator.listFiles(ListFilesRequest.getDefaultInstance()).getFileNamesList().forEach(System.out::println);
    }

    private static void verify(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String[] args) throws Exception {
        if (args.length < 3 || args.length > 4) {
            usage();
            return;
        }
        String expectedChecksum = requireChecksum(coordinator, args[1]);
        String localChecksum = ChecksumUtil.sha256Hex(Path.of(args[2]));
        if (!expectedChecksum.equals(localChecksum)) {
            throw new IllegalStateException("Verify failed: local file does not match remote checksum");
        }
        System.out.println("Checksum OK");
    }

    private static void checksum(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String[] args) {
        if (args.length < 2 || args.length > 3) {
            usage();
            return;
        }
        System.out.println(requireChecksum(coordinator, args[1]));
    }

    private static void storeChecksum(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String remoteName, String checksum) {
        var response = coordinator.storeChunk(Chunk.newBuilder()
                .setFileName(ChecksumFiles.forFile(remoteName))
                .setChunkIndex(0)
                .setData(com.google.protobuf.ByteString.copyFrom(checksum, StandardCharsets.UTF_8))
                .build());
        if (!response.getSuccess()) {
            throw new IllegalStateException("Upload stopped: " + response.getMessage());
        }
    }

    private static String readChecksum(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String remoteName) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Iterator<Chunk> chunks = coordinator.downloadFile(DownloadRequest.newBuilder().setFileName(ChecksumFiles.forFile(remoteName)).build());
            while (chunks.hasNext()) {
                output.write(chunks.next().getData().toByteArray());
            }
            return output.toString(StandardCharsets.UTF_8);
        } catch (StatusRuntimeException exception) {
            if (exception.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return null;
            }
            throw exception;
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Could not read checksum for " + remoteName, exception);
        }
    }

    private static String requireChecksum(CoordinatorGrpc.CoordinatorBlockingStub coordinator, String remoteName) {
        String checksum = readChecksum(coordinator, remoteName);
        if (checksum == null || checksum.isBlank()) {
            throw new IllegalStateException("No checksum stored for " + remoteName);
        }
        return checksum;
    }

    private static void usage() {
        System.out.println("Usage:\n  upload <local-file> <remote-name> [coordinator-host:port]\n  download <remote-name> <local-file> [coordinator-host:port]\n  verify <remote-name> <local-file> [coordinator-host:port]\n  checksum <remote-name> [coordinator-host:port]\n  list [coordinator-host:port]");
    }
}
