package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("hashtag")
public class Hashtag {
    @Id
    private String tag;

    public Hashtag(String tag) {
        this.tag = tag;
    }
}
