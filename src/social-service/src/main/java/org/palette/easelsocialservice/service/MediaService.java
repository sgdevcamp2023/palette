package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.MediaRequest;
import org.palette.easelsocialservice.persistence.domain.Media;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService {

    public List<Media> createMedias(List<MediaRequest> medias) {

        return null;
    }
}
