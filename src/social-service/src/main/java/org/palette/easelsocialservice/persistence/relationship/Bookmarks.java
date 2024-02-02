package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@Getter
@RelationshipProperties
public class Bookmarks {

    @RelationshipId
    private Long id;

    @TargetNode
    private final Paint paint;

    private final LocalDateTime createdAt;

    public Bookmarks(final Paint paint) {
        this.paint = paint;
        this.createdAt = LocalDateTime.now();
    }
}
