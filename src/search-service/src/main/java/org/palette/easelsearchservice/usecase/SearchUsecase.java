package org.palette.easelsearchservice.usecase;

import org.palette.dto.event.PaintCreatedEvent;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.springframework.stereotype.Service;

@Service
public class SearchUsecase {
    public void paintCreated(PaintCreatedEvent paintCreatedEvent) {
        System.out.println(paintCreatedEvent);
    }

    public PaintResponse searchAllPaints() {
        return null;
    }

    public PaintResponse searchRecentPaints() {
        return null;
    }
}
