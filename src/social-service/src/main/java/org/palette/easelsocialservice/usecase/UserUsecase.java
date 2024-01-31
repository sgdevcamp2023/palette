package org.palette.easelsocialservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.FollowUserRequest;
import org.palette.easelsocialservice.dto.request.LikePaintRequest;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUsecase {

    private final UserService userService;
    private final PaintService paintService;

    public void likePaint(Long userId, LikePaintRequest likePaintRequest) {
        Paint paint = paintService.getPaintById(likePaintRequest.paintId());
        userService.likePaint(userId, paint);
    }

    public void follow(Long userId, FollowUserRequest followUserRequest) {
        userService.follow(userId, followUserRequest.targetId());
    }
}
