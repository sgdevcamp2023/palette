package org.palette.easeluserservice.api;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.api.dto.request.EmailDuplicationVerifyRequest;
import org.palette.easeluserservice.api.dto.request.JoinRequest;
import org.palette.easeluserservice.api.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.usecase.UserUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserUsecase userUsecase;

    @PostMapping("verify-email")
    public ResponseEntity<EmailDuplicationVerifyResponse> verifyUsername(
            @RequestBody EmailDuplicationVerifyRequest emailDuplicationVerifyRequest
    ) {
        return ResponseEntity.ok(
                userUsecase.executeNicknameDuplicationVerify(
                        emailDuplicationVerifyRequest.email()
                )
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("join")
    public void join(
            @RequestBody JoinRequest joinRequest
    ) {
        userUsecase.executeJoin(
                joinRequest.email(),
                joinRequest.username(),
                joinRequest.password(),
                joinRequest.nickname(),
                joinRequest.introduce(),
                joinRequest.profilePath(),
                joinRequest.backgroundPath(),
                joinRequest.websitePath());
    }
}
