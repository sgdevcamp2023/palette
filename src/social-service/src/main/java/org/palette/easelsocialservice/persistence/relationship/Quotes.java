package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
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
