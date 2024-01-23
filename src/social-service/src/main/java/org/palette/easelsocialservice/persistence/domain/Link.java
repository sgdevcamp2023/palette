package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Link {
    @Id
    private Long lid;

    private String shortUrl;

    private String originalUrl;

    public Link(Long lid, String shortUrl, String originalUrl) {
        this.lid = lid;
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
    }

}
