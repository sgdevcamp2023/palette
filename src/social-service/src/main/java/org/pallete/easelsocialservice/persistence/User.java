package org.pallete.easelsocialservice.persistence;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("user")
public class User {
    @Id
    private Long id;
    private String username;
    private String nickname;
    private String imagePath;

    public User(Long id, String username, String nickname, String imagePath) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.imagePath = imagePath;
    }
}
