# Distributed File Storage

Dropbox-lite built with Java 17, gRPC, and Docker.

## What it does

- Splits uploads into 1 MiB chunks.
- Stores every chunk on two storage nodes by default.
- Checks nodes every five seconds. If one dies, the coordinator copies its surviving chunks to another live node.
- Streams the chunks back in order on download.

## Architecture

```text
File client -> Coordinator -> Storage node 1
                         -> Storage node 2   (replica)
                         -> Storage node 3   (recovery target)
```

The coordinator owns chunk-to-node metadata. Nodes only store chunk bytes.

`src/main/proto/distributed_storage.proto` is the API source of truth. Its generated Java/gRPC files are committed so a fresh clone builds without requiring a local Protocol Buffers compiler.

## Run locally

Build first:

```powershell
mvn test package
```

Use four terminals in this folder:

```powershell
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.node.StorageNodeMain --port 50061
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.node.StorageNodeMain --port 50062
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.node.StorageNodeMain --port 50063
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.coordinator.CoordinatorMain --port 50051 --nodes localhost:50061,localhost:50062,localhost:50063 --replicas 2
```

Upload, list, and download:

```powershell
java -jar target/distributed-file-storage-0.1.0.jar upload .\README.md README-copy.md
java -jar target/distributed-file-storage-0.1.0.jar list
java -jar target/distributed-file-storage-0.1.0.jar download README-copy.md .\downloaded-readme.md
```

Kill one storage-node process after upload. The coordinator should log recovery onto the unused node. The download still works as long as one replica survives.

## Run with Docker

```powershell
docker compose up --build
```

Docker is required only for this path. The Java/Maven path above works without it.

## Honest limits

This is an MVP, not Dropbox. The metadata catalog is in memory, so a coordinator restart forgets existing files. It also has no login system, encryption, checksum validation, persistent metadata database, or leader election. Those are the next real steps—not fake resume fluff.
