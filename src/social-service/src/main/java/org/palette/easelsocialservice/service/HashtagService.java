package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.persistence.HashtagRepository;
import org.palette.easelsocialservice.persistence.domain.Hashtag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public List<Hashtag> createHashtags(List<HashtagRequest> hashtags) {
        List<String> tags = hashtags.stream().map(HashtagRequest::tag).toList();
        return hashtagRepository.createHashtags(tags);
    }
}
