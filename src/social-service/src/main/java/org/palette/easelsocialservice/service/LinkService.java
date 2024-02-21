package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.common.PathEncoder;
import org.palette.easelsocialservice.dto.request.LinkRequest;
import org.palette.easelsocialservice.persistence.LinkRepository;
import org.palette.easelsocialservice.persistence.domain.Link;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    private static final long PADDING = 10_000_000;
    private final LinkRepository linkRepository;
    private final PathEncoder pathEncoder;

    public List<Link> createLinks(List<LinkRequest> linkRequests) {
        List<Link> links = new LinkedList<>();
        Long lid = generateLid();
        for (LinkRequest link : linkRequests) {
            String shortUrl = pathEncoder.encode(lid);
            links.add(new Link(lid++, shortUrl, link.link()));
        }
        return linkRepository.saveAll(links);
    }

    private Long generateLid() {
        Long lastLid = linkRepository.getLastLid();
        return lastLid != null ? lastLid + 1 : PADDING;
    }
}
