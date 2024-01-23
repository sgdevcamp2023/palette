package org.palette.easelauthservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.dto.request.EmailAuthRequest;
import org.palette.easelauthservice.usecase.AuthUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class EmailAuthController {

    private final AuthUsecase authUsecase;

    @PostMapping
    public ResponseEntity<Void> auth(
            @RequestBody EmailAuthRequest emailAuthRequest
    ) {
        authUsecase.verify(emailAuthRequest);
        return ResponseEntity.ok().build();
    }

}
