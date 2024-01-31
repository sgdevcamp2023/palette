package org.palette.easeluserservice.external;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.grpc.*;
import org.palette.passport.component.Passport;
import org.springframework.stereotype.Component;

@Component
public class GrpcSocialClient {

    @GrpcClient("social-service")
    private GSocialServiceGrpc.GSocialServiceBlockingStub gSocialServiceBlockingStub;

    public GCreateUserResponse createSocialUser(User user) {
        try {
            return gSocialServiceBlockingStub.createUser(
                    GCreateUserRequest.newBuilder()
                            .setId(user.getId())
                            .setUsername(user.getUsername())
                            .setImagePath(user.getProfile().staticContentPath().profileImagePath())
                            .setNickname(user.getProfile().nickname())
                            .setIsActive(user.getIsActivated())
                            .build());
        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }
    }

    public GLoadUserFollowInformationResponse loadUserFollowShipCount(
            Passport passport,
            User user
    ) {
        if (user == null) {
            return retrieveFollowShipMe(passport);
        }
        return retrieveFollowShipOther(user, passport);
    }

    private GLoadUserFollowInformationResponse retrieveFollowShipMe(final Passport passport) {
        try {
            if (passport.userInfo().deletedAt() == null) {
                return gSocialServiceBlockingStub.loadUserFollowInformation(
                        GLoadUserFollowInformationRequest.newBuilder()
                                .setPassport(GPassport.newBuilder()
                                        .setId(passport.userInfo().id())
                                        .setEmail(passport.userInfo().email())
                                        .setUsername(passport.userInfo().username())
                                        .setNickname(passport.userInfo().nickname())
                                        .setRole(passport.userInfo().role())
                                        .setAccessedAt(passport.userInfo().isActivated().toString())
                                        .setCreatedAt(passport.userInfo().createdAt())
                                        .setDeletedAt("")
                                        .setIntegrityKey(passport.integrityKey())
                                        .build()
                                )
                                .build()
                );
            }
            return gSocialServiceBlockingStub.loadUserFollowInformation(
                    GLoadUserFollowInformationRequest.newBuilder()
                            .setPassport(GPassport.newBuilder()
                                    .setId(passport.userInfo().id())
                                    .setEmail(passport.userInfo().email())
                                    .setUsername(passport.userInfo().username())
                                    .setNickname(passport.userInfo().nickname())
                                    .setRole(passport.userInfo().role())
                                    .setAccessedAt(passport.userInfo().isActivated().toString())
                                    .setCreatedAt(passport.userInfo().createdAt())
                                    .setDeletedAt(passport.userInfo().deletedAt())
                                    .setIntegrityKey(passport.integrityKey())
                                    .build()
                            )
                            .build()
            );

        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }
    }

    private GLoadUserFollowInformationResponse retrieveFollowShipOther(
            final User user,
            final Passport passport
    ) {
        try {
            if (user.getDeletedAt() == null) {
                return gSocialServiceBlockingStub.loadUserFollowInformation(
                        GLoadUserFollowInformationRequest.newBuilder()
                                .setPassport(GPassport.newBuilder()
                                        .setId(user.getId())
                                        .setEmail(user.getEmail())
                                        .setUsername(user.getUsername())
                                        .setNickname(user.getProfile().nickname())
                                        .setRole(user.getRole().toString())
                                        .setAccessedAt(user.getAccessedAt().toString())
                                        .setCreatedAt(user.getCreatedAt().toString())
                                        .setDeletedAt(user.getDeletedAt().toString())
                                        .setIntegrityKey(passport.integrityKey())
                                        .build()
                                )
                                .build()
                );
            }
            return gSocialServiceBlockingStub.loadUserFollowInformation(
                    GLoadUserFollowInformationRequest.newBuilder()
                            .setPassport(GPassport.newBuilder()
                                    .setId(user.getId())
                                    .setEmail(user.getEmail())
                                    .setUsername(user.getUsername())
                                    .setNickname(user.getProfile().nickname())
                                    .setRole(user.getRole().toString())
                                    .setAccessedAt(user.getAccessedAt().toString())
                                    .setCreatedAt(user.getCreatedAt().toString())
                                    .setDeletedAt(user.getDeletedAt().toString())
                                    .setIntegrityKey(passport.integrityKey())
                                    .build()
                            )
                            .build()
            );

        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }
    }
}
