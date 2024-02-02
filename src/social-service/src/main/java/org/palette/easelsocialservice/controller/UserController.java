package org.palette.easelsocialservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.FollowUserRequest;
import org.palette.easelsocialservice.dto.request.LikePaintRequest;
import org.palette.easelsocialservice.dto.request.MarkPaintRequest;
import org.palette.easelsocialservice.usecase.UserUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserUsecase userUsecase;

    @PostMapping("/{userId}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long userId,
            @RequestBody LikePaintRequest likePostRequest
    ) {
        userUsecase.likePaint(userId, likePostRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<Void> follow(
            @PathVariable Long userId,
            @RequestBody FollowUserRequest followUserRequest
    ) {
        userUsecase.follow(userId, followUserRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/mark")
    public ResponseEntity<Void> markPost(
            @PathVariable Long userId,
            @RequestBody MarkPaintRequest markPaintRequest
    ) {
        userUsecase.markPaint(userId, markPaintRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/mark/{paintId}")
    public ResponseEntity<Void> markPost(
            @PathVariable Long userId,
            @PathVariable Long paintId
    ) {
        System.out.println("userId = " + userId);
        System.out.println("paintId = " + paintId);
        userUsecase.deleteMarkPaint(userId, paintId);
        return ResponseEntity.ok().build();
    }
}
