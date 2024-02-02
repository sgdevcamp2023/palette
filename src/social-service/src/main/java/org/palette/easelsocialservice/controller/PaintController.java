package org.palette.easelsocialservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.InjectEaselAuthentication;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.dto.response.ThreadResponse;
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

    @InjectEaselAuthentication
    @PostMapping
    public ResponseEntity<PaintCreateResponse> create(
            @RequestBody PaintCreateRequest paintCreateRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        paintUsecase.createPaint(
                                EaselAuthenticationContext.getUserInfo().id(),
                                paintCreateRequest
                        )
                );
    }

    @InjectEaselAuthentication
    @PostMapping("/repaint")
    public ResponseEntity<Void> repaint(
            @RequestBody RepaintRequest repaintRequest
    ) {
        paintUsecase.repaint(
                EaselAuthenticationContext.getUserInfo().id(),
                repaintRequest
        );
        return ResponseEntity
                .ok()
                .build();
    }

    @InjectEaselAuthentication
    @GetMapping("/{paintId}")
    public ResponseEntity<PaintResponse> singleRead(
            @PathVariable Long paintId
    ) {
        return ResponseEntity
                .ok()
                .body(
                        paintUsecase.getSinglePaint(
                                EaselAuthenticationContext.getUserInfo().id(),
                                paintId
                        ));
    }

    @InjectEaselAuthentication
    @GetMapping("/{paintId}/before")
    public ResponseEntity<List<PaintResponse>> singleBeforeRead(
            @PathVariable Long paintId
    ) {
        return ResponseEntity
                .ok()
                .body(
                        paintUsecase.getSingleBefore(
                                EaselAuthenticationContext.getUserInfo().id(),
                                paintId
                        ));
    }

    @InjectEaselAuthentication
    @GetMapping("/{paintId}/after")
    public ResponseEntity<List<ThreadResponse>> singleAfterRead(
            @PathVariable Long paintId
    ) {
        return ResponseEntity
                .ok()
                .body(
                        paintUsecase.getSingleAfter(
                                EaselAuthenticationContext.getUserInfo().id(),
                                paintId
                        ));
    }

    @InjectEaselAuthentication
    @GetMapping("/{paintId}/quote-paints")
    public ResponseEntity<List<PaintResponse>> getQuotePaints(
            @PathVariable Long paintId
    ) {
        return ResponseEntity
                .ok()
                .body(
                        paintUsecase.getQuotePaints(
                                EaselAuthenticationContext.getUserInfo().id(),
                                paintId
                        ));
    }
}
