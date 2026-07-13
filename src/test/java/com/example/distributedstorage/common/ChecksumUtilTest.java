package com.example.distributedstorage.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChecksumUtilTest {
    @TempDir
    Path tempDir;

    @Test
    void computesSha256ForFileContent() throws Exception {
        Path file = tempDir.resolve("sample.txt");
        Files.writeString(file, "hello distributed storage", StandardCharsets.UTF_8);

        assertEquals("c3ce2d96d4060097af10bdb2e2ec49d2a843d8c83f45f80cbe107bce92f207bc", ChecksumUtil.sha256Hex(file));
    }

    @Test
    void hexEncodesBytes() {
        assertEquals("000fa5", ChecksumUtil.hex(new byte[]{0x00, 0x0f, (byte) 0xa5}));
    }
}
