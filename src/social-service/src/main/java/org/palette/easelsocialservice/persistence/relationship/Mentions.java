package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.User;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@RelationshipProperties
public class Mentions {
    @RelationshipId
    private Long id;

    @TargetNode
    private final User user;

    private final Integer start;

    private final Integer end;

    public Mentions(User user, int start, int end) {
        this.user = user;
        this.start = start;
        this.end = end;
    }
}
