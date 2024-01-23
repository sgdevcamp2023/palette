package org.palette.easelsocialservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.usecase.PaintUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
