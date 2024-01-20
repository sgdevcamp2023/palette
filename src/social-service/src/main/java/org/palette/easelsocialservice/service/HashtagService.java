package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.persistence.HashtagRepository;
import org.palette.easelsocialservice.persistence.LinkRepository;
import org.palette.easelsocialservice.persistence.MediaRepository;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {

    public void createHashtags(Long paintId, Optional<List<PaintCreateRequest.Hashtag>> hashtags) {
    }
}
