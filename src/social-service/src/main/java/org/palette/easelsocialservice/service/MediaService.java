package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.MediaRequest;
import org.palette.easelsocialservice.persistence.MediaRepository;
import org.palette.easelsocialservice.persistence.domain.Media;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MediaRepository mediaRepository;

    public List<Media> createMedias(List<MediaRequest> medias) {
        List<Media> mediaList = medias.stream()
                .map(mediaRequest -> new Media(mediaRequest.path(), mediaRequest.type()))
                .collect(Collectors.toList());
        return mediaRepository.saveAll(mediaList);
    }
}
