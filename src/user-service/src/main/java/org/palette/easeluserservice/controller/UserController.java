package org.palette.easeluserservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.InjectEaselAuthentication;
import org.palette.easeluserservice.dto.request.VerifyEmailDuplicationRequest;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.dto.request.UsernameDuplicationVerifyRequest;
import org.palette.easeluserservice.dto.response.EmailDuplicationVerifyResponse;
import org.palette.easeluserservice.dto.response.UsernameDuplicationVerifyResponse;
import org.palette.easeluserservice.dto.response.VerifyEmailDuplicationResponse;
import org.palette.easeluserservice.usecase.UserUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserUsecase userUsecase;

    @PostMapping("/verify-email")
    public ResponseEntity<VerifyEmailDuplicationResponse> verifyUsername(
            @RequestBody VerifyEmailDuplicationRequest verifyEmailDuplicationRequest
    ) {
        return ResponseEntity.ok(
                userUsecase.executeNicknameDuplicationVerify(
                        verifyEmailDuplicationRequest.email()
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

    @InjectEaselAuthentication
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{id}")
    public void retrieveOther(@PathVariable Long id) {
        userUsecase.retrieveOther(
                EaselAuthenticationContext.getUserInfo(),
                EaselAuthenticationContext.getIntegrityKey(),
                id
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/me")
    public void retrieveMe() {
        userUsecase.retrieveMe(
                EaselAuthenticationContext.getUserInfo(),
                EaselAuthenticationContext.getIntegrityKey()
        );
    }
}
