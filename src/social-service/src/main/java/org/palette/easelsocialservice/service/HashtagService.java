package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.persistence.domain.Hashtag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    public List<Hashtag> createHashtags(List<HashtagRequest> hashtags) {
        return null;
    }
}
