package org.palette.easeluserservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.easeluserservice.dto.request.EditProfileRequest;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.dto.response.RetrieveUserResponse;
import org.palette.easeluserservice.dto.response.UsernameDuplicationVerifyResponse;
import org.palette.easeluserservice.dto.response.VerifyEmailDuplicationResponse;
import org.palette.easeluserservice.external.GrpcAuthClient;
import org.palette.easeluserservice.external.GrpcNotificationClient;
import org.palette.easeluserservice.external.GrpcSocialClient;
import org.palette.easeluserservice.external.KafkaProducer;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.service.UserService;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
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
    private final GrpcNotificationClient grpcNotificationClient;
    private final KafkaProducer kafkaProducer;

    public VerifyEmailDuplicationResponse executeNicknameDuplicationVerify(
            String email
    ) {
        userService.isEmailAlreadyExists(email);
        return new VerifyEmailDuplicationResponse(Boolean.FALSE);
    }

    public UsernameDuplicationVerifyResponse executeUsernameDuplicationVerify(
            String username
    ) {
        userService.isUsernameAlreadyExists(username);
        return new UsernameDuplicationVerifyResponse(Boolean.FALSE);
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
                joinRequest.profileImagePath(),
                joinRequest.websitePath()
        );

        gRPCSocialClient.createSocialUser(user);
        grpcNotificationClient.createNotificationUser(user);
    }

    public RetrieveUserResponse retrieveOther(
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

        return new RetrieveUserResponse(
                retrieveTargetUser.getId(),
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

    public RetrieveUserResponse retrieveMe(
            UserInfo userInfo,
            String integrityKey
    ) {
        User user = userService.loadById(userInfo.id());

        final GLoadUserFollowInformationResponse gResponse = gRPCSocialClient.loadUserFollowShipCount(
                userInfo,
                null,
                integrityKey
        );

        return new RetrieveUserResponse(
                user.getId(),
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

    @Transactional
    public void editProfile(
            UserInfo userInfo,
            EditProfileRequest editProfileRequest
    ) {
        User user = userService.loadById(userInfo.id());
        user.editProfile(
                editProfileRequest.nickname(),
                editProfileRequest.introduce(),
                editProfileRequest.profileImagePath(),
                editProfileRequest.backgroundImagePath(),
                editProfileRequest.websitePath()
        );

        kafkaProducer.execute(convertToUpdateUserEvent(user));
    }

    private void validateJoinRequest(JoinRequest joinRequest, User user) {
        if (user.isUserNotAuthed()) {
            throw new BaseException(ExceptionType.USER_401_000001);
        }
        userService.isUsernameAlreadyExists(joinRequest.username());
    }

    private UpdateUserEvent convertToUpdateUserEvent(User user) {
        return new UpdateUserEvent(
                user.getId(),
                user.getProfile().nickname(),
                user.getProfile().introduce(),
                user.getProfile().staticContentPath().profileImagePath(),
                user.getProfile().staticContentPath().backgroundImagePath(),
                user.getProfile().staticContentPath().websitePath()
        );
    }
}
