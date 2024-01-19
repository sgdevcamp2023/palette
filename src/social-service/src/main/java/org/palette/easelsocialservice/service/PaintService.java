package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.persistence.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;
    private final HashtagRepository hashtagRepository;
    private final LinkRepository linkRepository;
    private final MediaRepository mediaRepository;

    public Long createPaint(Long userId, String text, Optional<String> s, Optional<String> string) {


        return null;
    }


}
