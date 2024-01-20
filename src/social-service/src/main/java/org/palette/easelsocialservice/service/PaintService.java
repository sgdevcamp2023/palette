package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.persistence.*;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;

    public Long createPaint(Long userId, String text, Optional<Long> inReplyToPaintId, Optional<Long> quotePaintId) {
        Paint paint = paintRepository.save(new Paint(text));


        return null;
    }




}
