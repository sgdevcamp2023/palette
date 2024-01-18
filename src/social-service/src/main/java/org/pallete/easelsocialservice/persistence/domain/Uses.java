package org.pallete.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Uses {
    @RelationshipId
    private Long id;

    @TargetNode
    private Media media;

    public Uses(Media media) {
        this.media = media;
    }
}
