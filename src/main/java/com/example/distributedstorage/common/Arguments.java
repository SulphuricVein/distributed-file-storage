package com.example.distributedstorage.common;

import java.util.HashMap;
import java.util.Map;

public final class Arguments {
    private Arguments() {
    }

    public static Map<String, String> parse(String[] args) {
        Map<String, String> values = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String argument = args[i];
            if (!argument.startsWith("--") || i + 1 >= args.length) {
                continue;
            }
            values.put(argument.substring(2), args[++i]);
        }
        return values;
    }
}

