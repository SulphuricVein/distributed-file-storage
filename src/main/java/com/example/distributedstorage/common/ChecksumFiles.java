package com.example.distributedstorage.common;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ChecksumFiles {
    private static final String PREFIX = "__dfs_internal__/checksums/";

    private ChecksumFiles() {
    }

    public static String forFile(String fileName) {
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(fileName.getBytes(StandardCharsets.UTF_8));
        return PREFIX + encoded + ".sha256";
    }

    public static boolean isChecksumFile(String fileName) {
        return fileName.startsWith(PREFIX);
    }
}
