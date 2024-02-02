package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Node
public class Link {
    @Id
    private final Long lid;

    private final String shortLink;

    private final String originalLink;

    public Link(Long lid, String shortLink, String originalLink) {
        this.lid = lid;
        this.shortLink = shortLink;
        this.originalLink = originalLink;
    }
}
