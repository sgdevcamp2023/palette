package org.palette.easelsocialservice.service;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.grpc.GCreateUserRequest;
import org.palette.grpc.GCreateUserResponse;
import org.palette.grpc.GSocialServiceGrpc;

@GrpcService
public class PaintService extends GSocialServiceGrpc.GSocialServiceImplBase {

    @Override
    public void createUser(GCreateUserRequest request, StreamObserver<GCreateUserResponse> responseStreamObserver) {
        GCreateUserResponse response = GCreateUserResponse.newBuilder()
                        .setMessage(true)
                                .build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

}
