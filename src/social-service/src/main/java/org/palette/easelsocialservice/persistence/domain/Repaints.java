package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Repaints {
    @Id
    private Long id;

    @TargetNode
    private Paint paint;

    public Repaints(Paint paint) {
        this.paint = paint;
    }
}
