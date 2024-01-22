package org.palette.easeluserservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.external.GrpcAuth;
import org.palette.easeluserservice.external.GrpcSocial;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.embed.Email;
import org.palette.easeluserservice.service.UserService;
import org.palette.grpc.GCreateUserRequest;
import org.palette.grpc.GSendEmailAuthRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserUsecase {

    private final UserService userService;
    private final GrpcSocial gRPCSocial;
    private final GrpcAuth gRPCAuth;

    public EmailDuplicationVerifyResponse executeNicknameDuplicationVerify(
            String email
    ) {
        return new EmailDuplicationVerifyResponse(
                userService.isEmailAlreadyExists(email) != null
        );
    }

    @Transactional
    public void executeTemporaryJoin(
            TemporaryJoinRequest temporaryJoinRequest
    ) {
        userService.isEmailAlreadyExists(temporaryJoinRequest.email());

        final User user = userService.createTemporaryUser(
                temporaryJoinRequest.email(),
                temporaryJoinRequest.nickname()
        );

        gRPCSendEmailAuth(user);
    }

    @Transactional
    public void executeJoin(
            JoinRequest joinRequest
    ) {
        final Optional<User> optionalUser = userService.loadByEmail(
                new Email(joinRequest.email())
        );

        User user = validateJoinRequest(joinRequest, optionalUser);

        user = userService.createCompletedUser(
                user,
                joinRequest.password(),
                joinRequest.username(),
                joinRequest.introduce(),
                joinRequest.profilePath(),
                joinRequest.backgroundPath(),
                joinRequest.websitePath()
        );

        gRPCCreateSocialUser(user);
    }

    private void gRPCSendEmailAuth(User user) {
        final GSendEmailAuthRequest gSendEmailAuthRequest = GSendEmailAuthRequest.newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail().value())
                .setNickname(user.getProfile().nickname().value())
                .build();

        gRPCAuth.sendEmailAuth(gSendEmailAuthRequest);
    }

    private void gRPCCreateSocialUser(User user) {
        final GCreateUserRequest gCreateUserRequest = GCreateUserRequest.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername().value())
                .setImagePath(user.getProfile().staticContentPath().profileImagePath())
                .setNickname(user.getProfile().nickname().value())
                .setIsActive(user.getAuthed())
                .getDefaultInstanceForType();

        gRPCSocial.createSocialUser(gCreateUserRequest);
    }

    private User validateJoinRequest(JoinRequest joinRequest, Optional<User> optionalUser) {
        if (optionalUser.isEmpty()) throw new BaseException(ExceptionType.USER_000004);
        User user = optionalUser.get();
        if (user.isUserNotAuthed()) throw new BaseException(ExceptionType.USER_000002);
        userService.isUsernameAlreadyExists(joinRequest.username());
        return user;
    }
}
