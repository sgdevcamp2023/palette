package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.persistence.*;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;

    public Long createPaint(Long userId, String text, Optional<Long> inReplyToPaintId, Optional<Long> quotePaintId) {
        Paint paint = new Paint(text);

        inReplyToPaintId.map(paintRepository::findByPid)
                .ifPresent(paintOpt -> paintOpt.ifPresentOrElse(
                        paint::addInReplyToPaint,
                        () -> { throw new NoSuchElementException("답장할 Paint를 찾을 수 없습니다. ID: " + inReplyToPaintId.get()); }
                ));

        quotePaintId.map(paintRepository::findByPid)
                .ifPresent(paintOpt -> paintOpt.ifPresentOrElse(
                        paint::addQuotePaint,
                        () -> { throw new NoSuchElementException("인용할 Paint를 찾을 수 없습니다. ID: " + quotePaintId.get()); }
                ));

        return paintRepository.save(paint).getPid();
    }
}
