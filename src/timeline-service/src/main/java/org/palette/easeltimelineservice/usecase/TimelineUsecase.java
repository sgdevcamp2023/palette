package org.palette.easeltimelineservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.LikedPaintEvent;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.UnlikedPaintEvent;
import org.palette.easeltimelineservice.external.grpc.GrpcSocialClient;
import org.palette.easeltimelineservice.persistence.domain.Paint;
import org.palette.easeltimelineservice.service.FollowerPaintMapService;
import org.palette.easeltimelineservice.service.PaintCacheService;
import org.palette.easeltimelineservice.service.PaintMetricsService;
import org.palette.easeltimelineservice.service.PaintResponse;
import org.palette.grpc.GFollowerIdsResponse;
import org.palette.grpc.GSocialServiceGrpc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimelineUsecase extends GSocialServiceGrpc.GSocialServiceImplBase {

    private final GrpcSocialClient gRPCSocialClient;
    private final FollowerPaintMapService followerPaintMapService;
    private final PaintCacheService paintCacheService;
    private final PaintMetricsService paintMetricsService;

    public void handlePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        final GFollowerIdsResponse followerIds = gRPCSocialClient.getFollowerIds(paintCreatedEvent.authorId());
        paintCacheService.cachePaint(paintCreatedEvent.id(), Paint.from(paintCreatedEvent));
        Optional.ofNullable(paintCreatedEvent.inReplyToPaintId())
                .ifPresent(paintMetricsService::incrementReplyCount);
        followerPaintMapService.addPaintToFollowersTimeline(followerIds.getFollowerIdsList(), paintCreatedEvent.id());
    }

    public List<PaintResponse> getFollowingTimeline(final Long userId) {
        List<Long> paintIds = followerPaintMapService.getFollowingTimelinePaintIds(userId);
        final List<Paint> paints = paintCacheService.getPaints(paintIds);
        return paints.stream().map(PaintResponse::from).toList();
    }

    public void handleLikedPaintEvent(final LikedPaintEvent likedPaintEvent) {
        paintMetricsService.incrementLikeCount(likedPaintEvent.paintId());
    }

    public void handleUnlikedPaintEvent(final UnlikedPaintEvent unlikedPaintEvent) {
        paintMetricsService.decrementLikeCount(unlikedPaintEvent.paintId());
    }

    public List<PaintResponse> getForYouTimeline(final Long userId) {
        List<Paint> paints = paintCacheService.getRandomPaints();
        List<Long> paintIds = followerPaintMapService.getFollowingTimelinePaintIds(userId);
        paints.removeIf(paint -> paintIds.contains(paint.getId()));
        paints.removeIf(paint -> paint.getAuthorId().equals(userId));
        return paints.stream().map(PaintResponse::from).toList();
    }
}
