package org.palette.easelsearchservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.service.SearchService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchUsecase {
    private final SearchService searchService;

    @Async
    public void paintCreated(PaintCreatedEvent paintCreatedEvent) {
        searchService.createPaint(paintCreatedEvent);
    }

    public PaintResponse searchAllPaints() {
        return null;
    }

    public PaintResponse searchRecentPaints() {
        return null;
    }
}
