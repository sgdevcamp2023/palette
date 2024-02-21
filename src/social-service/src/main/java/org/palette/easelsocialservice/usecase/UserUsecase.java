package org.palette.easelsocialservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.LikedPaintEvent;
import org.palette.dto.event.UnlikedPaintEvent;
import org.palette.easelsocialservice.dto.request.FollowUserRequest;
import org.palette.easelsocialservice.dto.request.LikePaintRequest;
import org.palette.easelsocialservice.dto.request.MarkPaintRequest;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.external.kafka.KafkaProducer;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUsecase {

    private final UserService userService;
    private final PaintService paintService;
    private final KafkaProducer kafkaProducer;

    public void likePaint(Long userId, LikePaintRequest likePaintRequest) {
        Paint paint = paintService.getPaintById(likePaintRequest.paintId());
        kafkaProducer.execute(new LikedPaintEvent(userId, paint.getPid()));
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

    public List<PaintResponse> getAllMarksPaintsByUserId(Long userId) {
        return paintService.getAllMarksPaintsByUserId(userId);
    }

    public List<PaintResponse> getAllLikingPaintsByUserId(Long userId) {
        return paintService.getAllLikingPaintsByUserId(userId);
    }

    public List<PaintResponse> getAllPaintsContainingMedia(Long userId) {
        return paintService.getAllContainingMediaByUserId(userId);
    }

    public void unlikePaint(final Long userId, final Long paintId) {
        kafkaProducer.execute(new UnlikedPaintEvent(userId, paintId));
        userService.unlike(userId, paintId);
    }

    @Async
    public void updateUserImagePath(final Long userId, final String nickname, final String imagePath) {
        userService.updateUser(userId, nickname, imagePath);
    }
}
