package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@RelationshipProperties
public class Likes {
    @RelationshipId
    private Long id;

    @TargetNode
    private final Paint paint;

    private final LocalDateTime createdAt;

    public Likes(final Paint paint) {
        this.paint = paint;
        this.createdAt = LocalDateTime.now();
    }
}
