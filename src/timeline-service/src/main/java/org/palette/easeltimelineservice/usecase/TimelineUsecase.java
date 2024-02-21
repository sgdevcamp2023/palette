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
import org.springframework.data.domain.Pageable;
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

    public List<PaintResponse> getFollowingTimeline(final Long userId, final Pageable pageable) {
        List<Long> paintIds = followerPaintMapService.getFollowingTimelinePaintIds(userId, pageable);
        return paintCacheService.getPaints(paintIds);
    }

    public void handleLikedPaintEvent(final LikedPaintEvent likedPaintEvent) {
        paintMetricsService.incrementLikeCount(likedPaintEvent.paintId());
    }

    public void handleUnlikedPaintEvent(final UnlikedPaintEvent unlikedPaintEvent) {
        paintMetricsService.decrementLikeCount(unlikedPaintEvent.paintId());
    }
}
