# Distributed File Storage

I built this to understand distributed file storage instead of just reading about it.

You upload a file. It gets split into chunks, copied to more than one node, and rebuilt on another node if one goes down.

## What is working

- Uploads are split into 1 MiB chunks.
- Each chunk is stored on two nodes by default.
- The coordinator keeps chunk metadata on disk, so a restart does not wipe the file list.
- The coordinator checks nodes every five seconds.
- If one node dies, it copies the surviving chunk to another live node.
- Downloads put the chunks back in the right order.

## Architecture

```text
File client -> Coordinator -> Storage node 1
                         -> Storage node 2   (replica)
                         -> Storage node 3   (recovery target)
```

The coordinator knows where every chunk is. Storage nodes only hold the chunk data.

The gRPC API starts at `src/main/proto/distributed_storage.proto`.

## Run locally

Build first:

```powershell
mvn test package
```

Open four terminals in this folder:

```powershell
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.node.StorageNodeMain --port 50061
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.node.StorageNodeMain --port 50062
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.node.StorageNodeMain --port 50063
java -cp target/distributed-file-storage-0.1.0.jar com.example.distributedstorage.coordinator.CoordinatorMain --port 50051 --nodes localhost:50061,localhost:50062,localhost:50063 --replicas 2 --metadata-file .\data\catalog.tsv
```

Upload, list, and download:

```powershell
java -jar target/distributed-file-storage-0.1.0.jar upload .\README.md README-copy.md
java -jar target/distributed-file-storage-0.1.0.jar list
java -jar target/distributed-file-storage-0.1.0.jar download README-copy.md .\downloaded-readme.md
```

Try killing one storage node after uploading a file. The coordinator should rebuild that copy on the unused node. Restarting the coordinator should still keep the file list because the metadata snapshot lives in `data/catalog.tsv`.

## Run with Docker

```powershell
docker compose up --build
```

You only need Docker for this path. The Java/Maven setup above works without it.

## What is missing

This is still a small MVP. The metadata now survives restart, but it is just one local snapshot file, not a real distributed metadata system. There is also no login, encryption, checksum validation, permanent database, or leader election yet.
