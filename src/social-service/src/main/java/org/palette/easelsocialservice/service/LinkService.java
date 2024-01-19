package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {

    public void createLinks(Long paintId, Optional<List<PaintCreateRequest.Link>> links) {
    }
}
