package org.palette.easeltimelineservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.service.FollowerPaintMapService;
import org.palette.easeltimelineservice.service.PaintCacheService;
import org.palette.easeltimelineservice.external.grpc.GrpcSocialClient;
import org.palette.easeltimelineservice.dto.PaintCreatedEvent;
import org.palette.grpc.GFollowerIdsResponse;
import org.palette.grpc.GSocialServiceGrpc;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimelineUsecase extends GSocialServiceGrpc.GSocialServiceImplBase {

    private final GrpcSocialClient gRPCSocialClient;
    private final FollowerPaintMapService followerPaintMapService;
    private final PaintCacheService paintCacheService;

    public void handlePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        final GFollowerIdsResponse followerIds = gRPCSocialClient.getFollowerIds(paintCreatedEvent.userId());
        paintCacheService.cachePaint(paintCreatedEvent.paintId(), paintCreatedEvent.paintResponse());
        followerPaintMapService.addPaintToFollowersTimeline(followerIds.getFollowerIdsList(), paintCreatedEvent.paintId());
    }
}
