package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.dto.response.*;
import org.palette.easelsocialservice.exception.BaseException;
import org.palette.easelsocialservice.exception.ExceptionType;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;
    private final PaintEntityConverter paintEntityConverter;

    public void createPaint(Paint paint) {
        paintRepository.save(paint);
    }

    public void repaint(User user, RepaintRequest repaintRequest) {
        Paint paint = paintRepository.findById(repaintRequest.originPaintId())
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.addRepaint(user);
        paintRepository.save(paint);
    }

    public PaintResponse getPaintById(Long userId, Long paintId) {
        Paint paint = paintRepository.findByPid(paintId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        PaintMetrics metrics = paintRepository.findMetricsByPidAndUid(userId, paintId);

        return paintEntityConverter.convertToPaintResponse(paint, metrics);
    }

    public Paint getPaintById(Long paintId) {
        return paintRepository.findByPid(paintId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
    }

    public List<PaintResponse> getPaintBeforeById(Long userId, Long paintId) {
        List<Paint> paints = distinctPaintsByPid(paintRepository.findAllBeforePaintByPid(paintId));

        return paintEntityConverter.convertToPaintResponse(userId, paints);
    }

    public List<ThreadResponse> getPaintAfterById(Long userId, Long paintId) {
        List<Paint> paints = distinctPaintsByPid(paintRepository.findAllAfterPaintByPid(paintId));

        List<ThreadResponse> threads = new LinkedList<>();
        int threadId = 0;
        for (Paint paint : paints) {
            threads.add(getThreadGroup(threadId++, userId, paint));
        }

        return threads;
    }

    public void viewSinglePaint(Long paintId) {
        paintRepository.updatePaintView(paintId);
    }

    public List<PaintResponse> getQuotePaintsById(final Long userId, final Long paintId) {
        List<Paint> paints = distinctPaintsByPid(paintRepository.findAllQuotePaintByPid(paintId));

        return paintEntityConverter.convertToPaintResponse(userId, paints);
    }

    private ThreadResponse getThreadGroup(Integer threadId, Long userId, Paint paint) {
        checkAndSetQuotePaint(paint);
        List<Paint> subPaints = distinctPaintsByPid(paintRepository.findAllAfterPaintsByPid(paint.getPid()));
        return new ThreadResponse(threadId, paintEntityConverter.convertToPaintResponse(userId, subPaints));
    }

    private void checkAndSetQuotePaint(Paint paint) {
        if (!paint.isHasQuote()) return;
        Paint quotePaint = paintRepository.findQuotePaintByPid(paint.getPid());
        paint.addQuotePaint(quotePaint);
    }

    private List<Paint> distinctPaintsByPid(List<Paint> paints) {
        return paints.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Paint::getPid))),
                        ArrayList::new
                ));
    }
}
