package com.example.distributedstorage.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: distributed_storage.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class StorageNodeGrpc {

  private StorageNodeGrpc() {}

  public static final java.lang.String SERVICE_NAME = "distributedstorage.StorageNode";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.distributedstorage.proto.Chunk,
      com.example.distributedstorage.proto.StoreChunkResponse> getStoreChunkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StoreChunk",
      requestType = com.example.distributedstorage.proto.Chunk.class,
      responseType = com.example.distributedstorage.proto.StoreChunkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.distributedstorage.proto.Chunk,
      com.example.distributedstorage.proto.StoreChunkResponse> getStoreChunkMethod() {
    io.grpc.MethodDescriptor<com.example.distributedstorage.proto.Chunk, com.example.distributedstorage.proto.StoreChunkResponse> getStoreChunkMethod;
    if ((getStoreChunkMethod = StorageNodeGrpc.getStoreChunkMethod) == null) {
      synchronized (StorageNodeGrpc.class) {
        if ((getStoreChunkMethod = StorageNodeGrpc.getStoreChunkMethod) == null) {
          StorageNodeGrpc.getStoreChunkMethod = getStoreChunkMethod =
              io.grpc.MethodDescriptor.<com.example.distributedstorage.proto.Chunk, com.example.distributedstorage.proto.StoreChunkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StoreChunk"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.Chunk.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.StoreChunkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageNodeMethodDescriptorSupplier("StoreChunk"))
              .build();
        }
      }
    }
    return getStoreChunkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.distributedstorage.proto.ChunkRequest,
      com.example.distributedstorage.proto.ChunkResponse> getGetChunkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetChunk",
      requestType = com.example.distributedstorage.proto.ChunkRequest.class,
      responseType = com.example.distributedstorage.proto.ChunkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.distributedstorage.proto.ChunkRequest,
      com.example.distributedstorage.proto.ChunkResponse> getGetChunkMethod() {
    io.grpc.MethodDescriptor<com.example.distributedstorage.proto.ChunkRequest, com.example.distributedstorage.proto.ChunkResponse> getGetChunkMethod;
    if ((getGetChunkMethod = StorageNodeGrpc.getGetChunkMethod) == null) {
      synchronized (StorageNodeGrpc.class) {
        if ((getGetChunkMethod = StorageNodeGrpc.getGetChunkMethod) == null) {
          StorageNodeGrpc.getGetChunkMethod = getGetChunkMethod =
              io.grpc.MethodDescriptor.<com.example.distributedstorage.proto.ChunkRequest, com.example.distributedstorage.proto.ChunkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetChunk"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.ChunkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.ChunkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageNodeMethodDescriptorSupplier("GetChunk"))
              .build();
        }
      }
    }
    return getGetChunkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.distributedstorage.proto.HealthRequest,
      com.example.distributedstorage.proto.HealthResponse> getHealthMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Health",
      requestType = com.example.distributedstorage.proto.HealthRequest.class,
      responseType = com.example.distributedstorage.proto.HealthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.distributedstorage.proto.HealthRequest,
      com.example.distributedstorage.proto.HealthResponse> getHealthMethod() {
    io.grpc.MethodDescriptor<com.example.distributedstorage.proto.HealthRequest, com.example.distributedstorage.proto.HealthResponse> getHealthMethod;
    if ((getHealthMethod = StorageNodeGrpc.getHealthMethod) == null) {
      synchronized (StorageNodeGrpc.class) {
        if ((getHealthMethod = StorageNodeGrpc.getHealthMethod) == null) {
          StorageNodeGrpc.getHealthMethod = getHealthMethod =
              io.grpc.MethodDescriptor.<com.example.distributedstorage.proto.HealthRequest, com.example.distributedstorage.proto.HealthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Health"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.HealthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.HealthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageNodeMethodDescriptorSupplier("Health"))
              .build();
        }
      }
    }
    return getHealthMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StorageNodeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageNodeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageNodeStub>() {
        @java.lang.Override
        public StorageNodeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageNodeStub(channel, callOptions);
        }
      };
    return StorageNodeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StorageNodeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageNodeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageNodeBlockingStub>() {
        @java.lang.Override
        public StorageNodeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageNodeBlockingStub(channel, callOptions);
        }
      };
    return StorageNodeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StorageNodeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageNodeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageNodeFutureStub>() {
        @java.lang.Override
        public StorageNodeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageNodeFutureStub(channel, callOptions);
        }
      };
    return StorageNodeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void storeChunk(com.example.distributedstorage.proto.Chunk request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.StoreChunkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStoreChunkMethod(), responseObserver);
    }

    /**
     */
    default void getChunk(com.example.distributedstorage.proto.ChunkRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.ChunkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetChunkMethod(), responseObserver);
    }

    /**
     */
    default void health(com.example.distributedstorage.proto.HealthRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.HealthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHealthMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service StorageNode.
   */
  public static abstract class StorageNodeImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return StorageNodeGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service StorageNode.
   */
  public static final class StorageNodeStub
      extends io.grpc.stub.AbstractAsyncStub<StorageNodeStub> {
    private StorageNodeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageNodeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageNodeStub(channel, callOptions);
    }

    /**
     */
    public void storeChunk(com.example.distributedstorage.proto.Chunk request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.StoreChunkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStoreChunkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getChunk(com.example.distributedstorage.proto.ChunkRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.ChunkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetChunkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void health(com.example.distributedstorage.proto.HealthRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.HealthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHealthMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service StorageNode.
   */
  public static final class StorageNodeBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<StorageNodeBlockingStub> {
    private StorageNodeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageNodeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageNodeBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.distributedstorage.proto.StoreChunkResponse storeChunk(com.example.distributedstorage.proto.Chunk request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStoreChunkMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.distributedstorage.proto.ChunkResponse getChunk(com.example.distributedstorage.proto.ChunkRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetChunkMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.distributedstorage.proto.HealthResponse health(com.example.distributedstorage.proto.HealthRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHealthMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service StorageNode.
   */
  public static final class StorageNodeFutureStub
      extends io.grpc.stub.AbstractFutureStub<StorageNodeFutureStub> {
    private StorageNodeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageNodeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageNodeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.distributedstorage.proto.StoreChunkResponse> storeChunk(
        com.example.distributedstorage.proto.Chunk request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStoreChunkMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.distributedstorage.proto.ChunkResponse> getChunk(
        com.example.distributedstorage.proto.ChunkRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetChunkMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.distributedstorage.proto.HealthResponse> health(
        com.example.distributedstorage.proto.HealthRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHealthMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_STORE_CHUNK = 0;
  private static final int METHODID_GET_CHUNK = 1;
  private static final int METHODID_HEALTH = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STORE_CHUNK:
          serviceImpl.storeChunk((com.example.distributedstorage.proto.Chunk) request,
              (io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.StoreChunkResponse>) responseObserver);
          break;
        case METHODID_GET_CHUNK:
          serviceImpl.getChunk((com.example.distributedstorage.proto.ChunkRequest) request,
              (io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.ChunkResponse>) responseObserver);
          break;
        case METHODID_HEALTH:
          serviceImpl.health((com.example.distributedstorage.proto.HealthRequest) request,
              (io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.HealthResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getStoreChunkMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.distributedstorage.proto.Chunk,
              com.example.distributedstorage.proto.StoreChunkResponse>(
                service, METHODID_STORE_CHUNK)))
        .addMethod(
          getGetChunkMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.distributedstorage.proto.ChunkRequest,
              com.example.distributedstorage.proto.ChunkResponse>(
                service, METHODID_GET_CHUNK)))
        .addMethod(
          getHealthMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.distributedstorage.proto.HealthRequest,
              com.example.distributedstorage.proto.HealthResponse>(
                service, METHODID_HEALTH)))
        .build();
  }

  private static abstract class StorageNodeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StorageNodeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.distributedstorage.proto.DistributedStorageProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StorageNode");
    }
  }

  private static final class StorageNodeFileDescriptorSupplier
      extends StorageNodeBaseDescriptorSupplier {
    StorageNodeFileDescriptorSupplier() {}
  }

  private static final class StorageNodeMethodDescriptorSupplier
      extends StorageNodeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    StorageNodeMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StorageNodeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StorageNodeFileDescriptorSupplier())
              .addMethod(getStoreChunkMethod())
              .addMethod(getGetChunkMethod())
              .addMethod(getHealthMethod())
              .build();
        }
      }
    }
    return result;
  }
}
