package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@NoArgsConstructor
public class User {
    @Id
    private Long uid;

    private String username;

    private String nickname;

    private String imagePath;

    private Boolean isActive;

    public User(Long uid, String username, String nickname, String imagePath, Boolean isActive) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.imagePath = imagePath;
        this.isActive = isActive;
    }



    public User(Long uid) {
        this.uid = uid;
    }
}
