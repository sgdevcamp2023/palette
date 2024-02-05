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
    private User user;

    private Integer start;

    private Integer end;

    public Mentions(User user, int start, int end) {
        this.user = user;
        this.start = start;
        this.end = end;
    }
}
