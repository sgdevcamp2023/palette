package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Quotes {
    @RelationshipId
    private Long id;

    @TargetNode
    private Paint paint;

    public Quotes(Paint paint) {
        this.paint = paint;
    }
}
