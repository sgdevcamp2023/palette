package org.palette.easeltimelineservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.InjectEaselAuthentication;
import org.palette.easeltimelineservice.service.PaintResponse;
import org.palette.easeltimelineservice.usecase.TimelineUsecase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineUsecase timelineUsecase;

    @GetMapping("/following")
    @InjectEaselAuthentication
    public ResponseEntity<List<PaintResponse>> getFollowingTimeline(@PageableDefault(size = 20) Pageable pageable) {
        final Long userId = EaselAuthenticationContext.getUserInfo().id();
        return ResponseEntity.ok(timelineUsecase.getFollowingTimeline(userId, pageable));
    }
}
