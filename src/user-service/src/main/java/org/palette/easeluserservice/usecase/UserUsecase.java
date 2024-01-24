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
import org.palette.easeluserservice.service.UserService;
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
        userService.isEmailAlreadyExists(email);
        return new EmailDuplicationVerifyResponse(Boolean.FALSE);
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
                joinRequest.email()
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
        gRPCAuth.sendEmailAuth(user);
    }

    private void gRPCCreateSocialUser(User user) {
        gRPCSocial.createSocialUser(user);
    }

    private User validateJoinRequest(JoinRequest joinRequest, Optional<User> optionalUser) {
        if (optionalUser.isEmpty()) throw new BaseException(ExceptionType.USER_404_000001);
        User user = optionalUser.get();
        if (user.isUserNotAuthed()) throw new BaseException(ExceptionType.USER_401_000001);
        userService.isUsernameAlreadyExists(joinRequest.username());
        return user;
    }
}
