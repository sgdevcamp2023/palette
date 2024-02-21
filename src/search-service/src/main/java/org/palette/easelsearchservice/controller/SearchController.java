package org.palette.easelsearchservice.controller;

import lombok.RequiredArgsConstructor;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.external.groc.GrpcSocialClient;
import org.palette.easelsearchservice.usecase.SearchUsecase;
import org.palette.grpc.GGetPaintsByIdsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paints/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchUsecase searchUsecase;
    private final GrpcSocialClient grpcSocialClient;

    @GetMapping("/all")
    public ResponseEntity<List<PaintResponse>> searchAllPaints(@ModelAttribute SearchRequest searchRequest) {
        return ResponseEntity.ok(searchUsecase.searchAllPaints(searchRequest));
    }

    @GetMapping("/recent")
    public ResponseEntity<PaintResponse> searchRecentPaints(@ModelAttribute SearchRequest searchRequest) {
        return ResponseEntity.ok(searchUsecase.searchRecentPaints());
    }

    @GetMapping("/test")
    public void searchRecentPaints() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        System.out.println(grpcSocialClient.getPaintsByIds(ids));
    }
}
