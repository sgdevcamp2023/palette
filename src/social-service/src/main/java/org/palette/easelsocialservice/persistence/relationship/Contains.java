package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.Link;
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

    private Integer start;

    private Integer end;

    public Contains(Link link, Integer start, Integer end) {
        this.link = link;
        this.start = start;
        this.end = end;
    }
}
