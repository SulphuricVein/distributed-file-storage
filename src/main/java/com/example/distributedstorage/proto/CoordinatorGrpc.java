package com.example.distributedstorage.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: distributed_storage.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CoordinatorGrpc {

  private CoordinatorGrpc() {}

  public static final java.lang.String SERVICE_NAME = "distributedstorage.Coordinator";

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
    if ((getStoreChunkMethod = CoordinatorGrpc.getStoreChunkMethod) == null) {
      synchronized (CoordinatorGrpc.class) {
        if ((getStoreChunkMethod = CoordinatorGrpc.getStoreChunkMethod) == null) {
          CoordinatorGrpc.getStoreChunkMethod = getStoreChunkMethod =
              io.grpc.MethodDescriptor.<com.example.distributedstorage.proto.Chunk, com.example.distributedstorage.proto.StoreChunkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StoreChunk"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.Chunk.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.StoreChunkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CoordinatorMethodDescriptorSupplier("StoreChunk"))
              .build();
        }
      }
    }
    return getStoreChunkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.distributedstorage.proto.DownloadRequest,
      com.example.distributedstorage.proto.Chunk> getDownloadFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadFile",
      requestType = com.example.distributedstorage.proto.DownloadRequest.class,
      responseType = com.example.distributedstorage.proto.Chunk.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.example.distributedstorage.proto.DownloadRequest,
      com.example.distributedstorage.proto.Chunk> getDownloadFileMethod() {
    io.grpc.MethodDescriptor<com.example.distributedstorage.proto.DownloadRequest, com.example.distributedstorage.proto.Chunk> getDownloadFileMethod;
    if ((getDownloadFileMethod = CoordinatorGrpc.getDownloadFileMethod) == null) {
      synchronized (CoordinatorGrpc.class) {
        if ((getDownloadFileMethod = CoordinatorGrpc.getDownloadFileMethod) == null) {
          CoordinatorGrpc.getDownloadFileMethod = getDownloadFileMethod =
              io.grpc.MethodDescriptor.<com.example.distributedstorage.proto.DownloadRequest, com.example.distributedstorage.proto.Chunk>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.DownloadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.Chunk.getDefaultInstance()))
              .setSchemaDescriptor(new CoordinatorMethodDescriptorSupplier("DownloadFile"))
              .build();
        }
      }
    }
    return getDownloadFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.distributedstorage.proto.ListFilesRequest,
      com.example.distributedstorage.proto.ListFilesResponse> getListFilesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListFiles",
      requestType = com.example.distributedstorage.proto.ListFilesRequest.class,
      responseType = com.example.distributedstorage.proto.ListFilesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.distributedstorage.proto.ListFilesRequest,
      com.example.distributedstorage.proto.ListFilesResponse> getListFilesMethod() {
    io.grpc.MethodDescriptor<com.example.distributedstorage.proto.ListFilesRequest, com.example.distributedstorage.proto.ListFilesResponse> getListFilesMethod;
    if ((getListFilesMethod = CoordinatorGrpc.getListFilesMethod) == null) {
      synchronized (CoordinatorGrpc.class) {
        if ((getListFilesMethod = CoordinatorGrpc.getListFilesMethod) == null) {
          CoordinatorGrpc.getListFilesMethod = getListFilesMethod =
              io.grpc.MethodDescriptor.<com.example.distributedstorage.proto.ListFilesRequest, com.example.distributedstorage.proto.ListFilesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.ListFilesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.distributedstorage.proto.ListFilesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CoordinatorMethodDescriptorSupplier("ListFiles"))
              .build();
        }
      }
    }
    return getListFilesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CoordinatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CoordinatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CoordinatorStub>() {
        @java.lang.Override
        public CoordinatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CoordinatorStub(channel, callOptions);
        }
      };
    return CoordinatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CoordinatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CoordinatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CoordinatorBlockingStub>() {
        @java.lang.Override
        public CoordinatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CoordinatorBlockingStub(channel, callOptions);
        }
      };
    return CoordinatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CoordinatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CoordinatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CoordinatorFutureStub>() {
        @java.lang.Override
        public CoordinatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CoordinatorFutureStub(channel, callOptions);
        }
      };
    return CoordinatorFutureStub.newStub(factory, channel);
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
    default void downloadFile(com.example.distributedstorage.proto.DownloadRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.Chunk> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadFileMethod(), responseObserver);
    }

    /**
     */
    default void listFiles(com.example.distributedstorage.proto.ListFilesRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.ListFilesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListFilesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Coordinator.
   */
  public static abstract class CoordinatorImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CoordinatorGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Coordinator.
   */
  public static final class CoordinatorStub
      extends io.grpc.stub.AbstractAsyncStub<CoordinatorStub> {
    private CoordinatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CoordinatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CoordinatorStub(channel, callOptions);
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
    public void downloadFile(com.example.distributedstorage.proto.DownloadRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.Chunk> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listFiles(com.example.distributedstorage.proto.ListFilesRequest request,
        io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.ListFilesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Coordinator.
   */
  public static final class CoordinatorBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CoordinatorBlockingStub> {
    private CoordinatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CoordinatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CoordinatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.distributedstorage.proto.StoreChunkResponse storeChunk(com.example.distributedstorage.proto.Chunk request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStoreChunkMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.example.distributedstorage.proto.Chunk> downloadFile(
        com.example.distributedstorage.proto.DownloadRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.distributedstorage.proto.ListFilesResponse listFiles(com.example.distributedstorage.proto.ListFilesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListFilesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Coordinator.
   */
  public static final class CoordinatorFutureStub
      extends io.grpc.stub.AbstractFutureStub<CoordinatorFutureStub> {
    private CoordinatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CoordinatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CoordinatorFutureStub(channel, callOptions);
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
    public com.google.common.util.concurrent.ListenableFuture<com.example.distributedstorage.proto.ListFilesResponse> listFiles(
        com.example.distributedstorage.proto.ListFilesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListFilesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_STORE_CHUNK = 0;
  private static final int METHODID_DOWNLOAD_FILE = 1;
  private static final int METHODID_LIST_FILES = 2;

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
        case METHODID_DOWNLOAD_FILE:
          serviceImpl.downloadFile((com.example.distributedstorage.proto.DownloadRequest) request,
              (io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.Chunk>) responseObserver);
          break;
        case METHODID_LIST_FILES:
          serviceImpl.listFiles((com.example.distributedstorage.proto.ListFilesRequest) request,
              (io.grpc.stub.StreamObserver<com.example.distributedstorage.proto.ListFilesResponse>) responseObserver);
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
          getDownloadFileMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.example.distributedstorage.proto.DownloadRequest,
              com.example.distributedstorage.proto.Chunk>(
                service, METHODID_DOWNLOAD_FILE)))
        .addMethod(
          getListFilesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.distributedstorage.proto.ListFilesRequest,
              com.example.distributedstorage.proto.ListFilesResponse>(
                service, METHODID_LIST_FILES)))
        .build();
  }

  private static abstract class CoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CoordinatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.distributedstorage.proto.DistributedStorageProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Coordinator");
    }
  }

  private static final class CoordinatorFileDescriptorSupplier
      extends CoordinatorBaseDescriptorSupplier {
    CoordinatorFileDescriptorSupplier() {}
  }

  private static final class CoordinatorMethodDescriptorSupplier
      extends CoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CoordinatorMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (CoordinatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CoordinatorFileDescriptorSupplier())
              .addMethod(getStoreChunkMethod())
              .addMethod(getDownloadFileMethod())
              .addMethod(getListFilesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
