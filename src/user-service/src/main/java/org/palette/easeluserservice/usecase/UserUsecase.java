package org.palette.easeluserservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.api.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserUsecase {

    private final UserService userService;

    public EmailDuplicationVerifyResponse executeNicknameDuplicationVerify(
            String email
    ) {
        if (userService.isEmailAlreadyExists(email) != null) {
            return new EmailDuplicationVerifyResponse(true);
        }

        return new EmailDuplicationVerifyResponse(false);
    }

    @Transactional
    public void executeJoin(
            String email,
            String password,
            String username,
            String nickname,
            Optional<String> introduce,
            Optional<String> profileImagePath,
            Optional<String> backgroundImagePath,
            Optional<String> websitePath
    ) {
        userService.createUser(
                email,
                password,
                username,
                nickname,
                introduce,
                profileImagePath,
                backgroundImagePath,
                websitePath
        );

        //TODO GDB요청 구문 필요
    }
}
