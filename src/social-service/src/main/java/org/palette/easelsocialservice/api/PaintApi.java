package org.palette.easelsocialservice.api;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.usecase.PaintUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paints")
@RequiredArgsConstructor
public class PaintApi {

    private final PaintUsecase paintUsecase;

    @PostMapping
    public ResponseEntity<PaintCreateResponse> create(
            @RequestBody PaintCreateRequest paintCreateRequest
    ) {
        // TODO: 사용자 ID 추가하기
        return ResponseEntity.ok(
                paintUsecase.createPaint(0L,
                        paintCreateRequest)
        );
    }
}
