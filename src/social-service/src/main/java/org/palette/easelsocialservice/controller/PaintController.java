package org.palette.easelsocialservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.dto.response.ThreadResponse;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.usecase.PaintUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paints")
@RequiredArgsConstructor
public class PaintController {

    private final PaintUsecase paintUsecase;

    @PostMapping
    public ResponseEntity<PaintCreateResponse> create(
            @RequestBody PaintCreateRequest paintCreateRequest
    ) {
        // TODO: 사용자 ID 추가하기
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paintUsecase.createPaint(100L, paintCreateRequest));
    }

    @PostMapping("/repaint")
    public ResponseEntity<Void> repaint(
            @RequestBody RepaintRequest repaintRequest
    ) {
        // TODO: 사용자 ID 추가하기
        paintUsecase.repaint(100L, repaintRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{paintId}")
    public ResponseEntity<PaintResponse> singleRead(
            @PathVariable Long paintId
    ) {
        // TODO: 사용자 ID 추가하기
        return ResponseEntity.ok().body(paintUsecase.getSinglePaint(100L, paintId));
    }

    @GetMapping("/{paintId}/before")
    public ResponseEntity<List<PaintResponse>> singleBeforeRead(
            @PathVariable Long paintId
    ) {
        // TODO: 사용자 ID 추가하기
        return ResponseEntity.ok().body(paintUsecase.getSingleBefore(100L, paintId));
    }

    @GetMapping("/{paintId}/after")
    public ResponseEntity<List<ThreadResponse>> singleAfterRead(
            @PathVariable Long paintId
    ) {
        // TODO: 사용자 ID 추가하기
        return ResponseEntity.ok().body(paintUsecase.getSingleAfter(100L, paintId));
    }
}
