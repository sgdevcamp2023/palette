package org.pallete.easelsocialservice.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;

@Node("paint")
public class Paint {
    @Id @GeneratedValue(PaintIdGenerator.class)
    private String pid;

    @Relationship(type = "CREATES", direction = Relationship.Direction.INCOMING)
    private Creates author;

    @Relationship(type = "CONTAINS")
    private List<Contains> links;
    
    private String content;
    
    private Integer views;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public Paint(String content) {
        this.content = content;
        this.views = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void setAuthor(User user) {
        this.author = new Creates(user);
    }

    public void addLink(Link link) {
        this.links.add(new Contains(link));
    }
}
