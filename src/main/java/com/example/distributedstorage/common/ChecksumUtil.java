package com.example.distributedstorage.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ChecksumUtil {
    private static final int BUFFER_SIZE = 8192;

    private ChecksumUtil() {
    }

    public static String sha256Hex(Path path) throws IOException {
        try (InputStream input = Files.newInputStream(path)) {
            return sha256Hex(input);
        }
    }

    public static String sha256Hex(InputStream input) throws IOException {
        MessageDigest digest = newSha256();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) >= 0) {
            digest.update(buffer, 0, bytesRead);
        }
        return hex(digest.digest());
    }

    public static String hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) {
            builder.append(String.format("%02x", value));
        }
        return builder.toString();
    }

    public static MessageDigest newSha256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }
}
