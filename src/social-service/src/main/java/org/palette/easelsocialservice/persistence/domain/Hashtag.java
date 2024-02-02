package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
public class Hashtag {
    @Id
    private final String tag;

    public Hashtag(String tag) {
        this.tag = tag;
    }

}
