package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Node
public class Link {
    @Id
    private Long lid;

    private String shortLink;

    private String originalLink;

    public Link(Long lid, String shortLink, String originalLink) {
        this.lid = lid;
        this.shortLink = shortLink;
        this.originalLink = originalLink;
    }
}
