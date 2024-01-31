package org.palette.easeluserservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.dto.response.UserRetrieveResponse;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.external.GrpcAuthClient;
import org.palette.easeluserservice.external.GrpcSocialClient;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.service.UserService;
import org.palette.grpc.GLoadUserFollowInformationResponse;
import org.palette.passport.component.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserUsecase {

    private final UserService userService;
    private final GrpcSocialClient gRPCSocialClient;
    private final GrpcAuthClient gRPCAuthClient;

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

        gRPCAuthClient.sendEmailAuth(user);
    }

    @Transactional
    public void executeJoin(
            JoinRequest joinRequest
    ) {
        User user = userService.loadByEmail(joinRequest.email());
        validateJoinRequest(joinRequest, user);

        user = userService.createCompletedUser(
                user,
                joinRequest.password(),
                joinRequest.username(),
                joinRequest.introduce(),
                joinRequest.profilePath(),
                joinRequest.backgroundPath(),
                joinRequest.websitePath()
        );

        gRPCSocialClient.createSocialUser(user);
    }

    public UserRetrieveResponse retrieveOther(
            UserInfo userInfo,
            String integrityKey,
            Long retrieveTargetUserId
    ) {
        User retrieveTargetUser = userService.loadById(retrieveTargetUserId);

        final GLoadUserFollowInformationResponse gResponse = gRPCSocialClient.loadUserFollowShipCount(
                userInfo,
                retrieveTargetUser,
                integrityKey
        );

        return new UserRetrieveResponse(
                retrieveTargetUser.getProfile().staticContentPath().backgroundImagePath(),
                retrieveTargetUser.getProfile().staticContentPath().profileImagePath(),
                retrieveTargetUser.getUsername(),
                retrieveTargetUser.getProfile().nickname(),
                retrieveTargetUser.getProfile().introduce(),
                retrieveTargetUser.getProfile().staticContentPath().websitePath(),
                retrieveTargetUser.getCreatedAt(),
                gResponse.getFollowingCount(),
                gResponse.getFollowerCount()
        );
    }

    public UserRetrieveResponse retrieveMe(
            UserInfo userInfo,
            String integrityKey
    ) {
        User user = userService.loadById(userInfo.id());

        final GLoadUserFollowInformationResponse gResponse = gRPCSocialClient.loadUserFollowShipCount(
                userInfo,
                null,
                integrityKey
        );

        return new UserRetrieveResponse(
                user.getProfile().staticContentPath().backgroundImagePath(),
                user.getProfile().staticContentPath().profileImagePath(),
                user.getUsername(),
                user.getProfile().nickname(),
                user.getProfile().introduce(),
                user.getProfile().staticContentPath().websitePath(),
                user.getCreatedAt(),
                gResponse.getFollowingCount(),
                gResponse.getFollowerCount()
        );
    }

    private void validateJoinRequest(JoinRequest joinRequest, User user) {
        if (user.isUserNotAuthed()) {
            throw new BaseException(ExceptionType.USER_401_000001);
        }
        userService.isUsernameAlreadyExists(joinRequest.username());
    }
}
