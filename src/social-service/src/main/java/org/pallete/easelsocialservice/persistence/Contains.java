package org.pallete.easelsocialservice.persistence;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@RelationshipProperties
public class Contains {
    @RelationshipId
    private Long id;

    @TargetNode
    private Link link;

    public Contains(Link link) {
        this.link = link;
    }
}
