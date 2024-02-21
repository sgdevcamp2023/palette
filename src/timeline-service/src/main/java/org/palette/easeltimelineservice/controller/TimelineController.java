package org.palette.easeltimelineservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.InjectEaselAuthentication;
import org.palette.aop.InternalErrorLogging;
import org.palette.easeltimelineservice.service.PaintResponse;
import org.palette.easeltimelineservice.usecase.TimelineUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineUsecase timelineUsecase;

    @GetMapping("/following")
    @InjectEaselAuthentication
    @InternalErrorLogging
    public ResponseEntity<List<PaintResponse>> getFollowingTimeline() {
        final Long userId = EaselAuthenticationContext.getUserInfo().id();
        return ResponseEntity.ok(timelineUsecase.getFollowingTimeline(userId));
    }

    @GetMapping("/for-you")
    @InjectEaselAuthentication
    @InternalErrorLogging
    public ResponseEntity<List<PaintResponse>> getForYouTimeline() {
        final Long userId = EaselAuthenticationContext.getUserInfo().id();
        return ResponseEntity.ok(timelineUsecase.getForYouTimeline(userId));
    }
}
