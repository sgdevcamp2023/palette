package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Media {
    @Id
    private String path;

    private String type;

    public Media(String path, String type) {
        this.path = path;
        this.type = type;
    }
}
