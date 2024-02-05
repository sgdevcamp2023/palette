package org.palette.easelsocialservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.FollowUserRequest;
import org.palette.easelsocialservice.dto.request.LikePaintRequest;
import org.palette.easelsocialservice.dto.request.MarkPaintRequest;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void markPaint(Long userId, MarkPaintRequest markPaintRequest) {
        Paint paint = paintService.getPaintById(markPaintRequest.paintId());
        userService.markPaint(userId, paint);
    }

    public void deleteMarkPaint(Long userId, Long paintId) {
        userService.deleteMarkPaint(userId, paintId);
    }

    public List<PaintResponse> getAllPaintsInMyPage(Long userId) {
        return paintService.getAllPaintsByUserId(userId);
    }

    public List<PaintResponse> getAllRepliesByUserId(Long userId) {
        return paintService.getAllRepliesByUserId(userId);
    }

    public List<PaintResponse> getAllPaintsContainingMedia(Long userId) {
        return paintService.getAllContainingMediaByUserId(userId);
    }

    public void unlikePaint(final Long userId, final Long paintId) {
        userService.unlike(userId, paintId);
    }
}
