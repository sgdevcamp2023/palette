package org.pallete.easelsocialservice.persistence.domain;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@RelationshipProperties
public class Creates {
    @RelationshipId
    private Long id;

    @TargetNode
    private User user;

    public Creates(User user) {
        this.user = user;
    }
}
