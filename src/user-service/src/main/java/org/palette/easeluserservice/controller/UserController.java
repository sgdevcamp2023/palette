package org.palette.easeluserservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.dto.request.EmailDuplicationVerifyRequest;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.dto.request.UsernameDuplicationVerifyRequest;
import org.palette.easeluserservice.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.dto.response.UsernameDuplicationVerifyResponse;
import org.palette.easeluserservice.usecase.UserUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserUsecase userUsecase;

    @PostMapping("/verify-email")
    public ResponseEntity<EmailDuplicationVerifyResponse> verifyEmail(
            @RequestBody EmailDuplicationVerifyRequest emailDuplicationVerifyRequest
    ) {
        return ResponseEntity.ok(
                userUsecase.executeEmailDuplicationVerify(
                        emailDuplicationVerifyRequest.email()
                )
        );
    }

    @PostMapping("/verify-username")
    public ResponseEntity<UsernameDuplicationVerifyResponse> verifyUsername(
            @RequestBody UsernameDuplicationVerifyRequest usernameDuplicationVerifyRequest
    ) {
        return ResponseEntity.ok(
                userUsecase.executeUsernameDuplicationVerify(
                        usernameDuplicationVerifyRequest.username()
                )
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/temporary-join")
    public void temporaryJoin(
            @RequestBody TemporaryJoinRequest temporaryJoinRequest
    ) {
        userUsecase.executeTemporaryJoin(temporaryJoinRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public void join(
            @RequestBody JoinRequest joinRequest
    ) {
        userUsecase.executeJoin(joinRequest);
    }
}