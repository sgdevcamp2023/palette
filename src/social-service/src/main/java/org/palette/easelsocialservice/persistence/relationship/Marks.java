package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@Getter
@RelationshipProperties
public class Marks {
    @RelationshipId
    private Long id;

    @TargetNode
    private Paint paint;

    private LocalDateTime createdAt;

    public Marks(final Paint paint) {
        this.paint = paint;
        this.createdAt = LocalDateTime.now();
    }
}
