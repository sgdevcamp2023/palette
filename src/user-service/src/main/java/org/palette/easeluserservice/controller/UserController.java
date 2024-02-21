package org.palette.easeluserservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.dto.request.EmailDuplicationVerifyRequest;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.usecase.UserUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

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
    @PostMapping("temporary-join")
    public void temporaryJoin(
            @RequestBody TemporaryJoinRequest temporaryJoinRequest
    ) {
        userUsecase.executeTemporaryJoin(temporaryJoinRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("join")
    public void join(
            @RequestBody JoinRequest joinRequest
    ) {
        userUsecase.executeJoin(joinRequest);
    }
}
