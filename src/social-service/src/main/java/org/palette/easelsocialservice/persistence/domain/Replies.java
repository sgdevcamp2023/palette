package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Replies {

    @RelationshipId
    private Long id;

    @TargetNode
    private Paint paint;

    public Replies(Paint paint) {
        this.paint = paint;
    }
}
