package org.palette.easelsearchservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.service.SearchService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchUsecase {
    private final SearchService searchService;

    @Async
    public void paintCreated(PaintCreatedEvent paintCreatedEvent) {
        searchService.createPaint(paintCreatedEvent);
    }

    public List<PaintResponse> searchAllPaints(final SearchRequest searchRequest) {
        return searchService.searchAllPaints(searchRequest);
    }

    public PaintResponse searchRecentPaints() {
        return null;
    }
}
