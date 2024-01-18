package org.palette.easelsocialservice.persistence.domain;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("user")
public class User {
    @Id
    private Long id;

    private String username;

    private String nickname;

    private String imagePath;

    private Boolean isActive;

    public User(Long id, String username, String nickname, String imagePath, Boolean isActive) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.imagePath = imagePath;
        this.isActive = isActive;
    }
}
