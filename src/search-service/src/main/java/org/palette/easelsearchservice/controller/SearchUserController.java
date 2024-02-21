package org.palette.easelsearchservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.UserResponse;
import org.palette.easelsearchservice.usecase.SearchUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchUserController {

    private final SearchUsecase searchUsecase;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> searchAllUsers(@ModelAttribute SearchRequest searchRequest) {
        return ResponseEntity.ok(searchUsecase.searchAllUsers(searchRequest));
    }
}
