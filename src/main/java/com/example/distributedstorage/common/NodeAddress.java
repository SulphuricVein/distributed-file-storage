package com.example.distributedstorage.common;

import java.util.Objects;

public record NodeAddress(String host, int port) {
    public NodeAddress {
        Objects.requireNonNull(host, "host");
        if (host.isBlank() || port < 1 || port > 65535) {
            throw new IllegalArgumentException("Invalid node address");
        }
    }

    public static NodeAddress parse(String value) {
        int separator = value.lastIndexOf(':');
        if (separator <= 0 || separator == value.length() - 1) {
            throw new IllegalArgumentException("Node must be host:port, got: " + value);
        }
        return new NodeAddress(value.substring(0, separator), Integer.parseInt(value.substring(separator + 1)));
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}

