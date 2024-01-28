package org.palette.easelsocialservice.persistence.relationship;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.domain.Hashtag;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@RelationshipProperties
public class Tags {
    @RelationshipId
    private Long id;

    @TargetNode
    private Hashtag hashtag;

    private Integer start;

    private Integer end;

    public Tags(Hashtag hashtag, Integer start, Integer end) {
        this.hashtag = hashtag;
        this.start = start;
        this.end = end;
    }
}
