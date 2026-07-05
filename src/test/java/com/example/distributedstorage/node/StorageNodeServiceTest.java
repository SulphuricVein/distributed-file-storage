package com.example.distributedstorage.node;

import com.example.distributedstorage.proto.Chunk;
import com.example.distributedstorage.proto.ChunkRequest;
import com.example.distributedstorage.proto.ChunkResponse;
import com.example.distributedstorage.proto.StoreChunkResponse;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageNodeServiceTest {
    @TempDir
    Path tempDirectory;

    @Test
    void storesAndReadsAChunk() {
        StorageNodeService service = new StorageNodeService(tempDirectory, "node-test");
        Result<StoreChunkResponse> write = new Result<>();
        service.storeChunk(Chunk.newBuilder().setFileName("folder/report.txt").setChunkIndex(0).setData(ByteString.copyFromUtf8("hello")).build(), write);

        Result<ChunkResponse> read = new Result<>();
        service.getChunk(ChunkRequest.newBuilder().setFileName("folder/report.txt").setChunkIndex(0).build(), read);

        assertTrue(write.value.getSuccess());
        assertTrue(read.value.getFound());
        assertEquals("hello", read.value.getData().toStringUtf8());
    }

    private static final class Result<T> implements StreamObserver<T> {
        private T value;

        @Override public void onNext(T value) { this.value = value; }
        @Override public void onError(Throwable throwable) { throw new AssertionError(throwable); }
        @Override public void onCompleted() { }
    }
}
