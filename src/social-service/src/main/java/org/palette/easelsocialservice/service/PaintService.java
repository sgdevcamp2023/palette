package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.persistence.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;

    public Long createPaint(Long userId, String text, Optional<Long> inReplyToPostId, Optional<Long> quotePaintId) {


        return null;
    }


}
