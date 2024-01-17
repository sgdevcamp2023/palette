package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;

@Node("paint")
public class Paint {
    @Id @GeneratedValue(PaintIdGenerator.class)
    private String pid;
    private String content;
    private Integer views;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Paint(String content) {
        this.content = content;
        this.views = 0;
        this.createdAt = LocalDateTime.now();
    }
}
