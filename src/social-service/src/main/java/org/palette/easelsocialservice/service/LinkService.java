package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.LinkRequest;
import org.palette.easelsocialservice.persistence.domain.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    public List<Link> createLinks(List<LinkRequest> links) {
        return null;
    }
}
