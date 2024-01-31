package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.palette.easelsocialservice.persistence.relationship.Contains;
import org.palette.easelsocialservice.persistence.relationship.Follows;
import org.palette.easelsocialservice.persistence.relationship.Likes;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.LinkedList;
import java.util.List;

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

    @Relationship(type = "LIKES")
    private List<Likes> likes;

    @Relationship(type = "FOLLOWS")
    private List<Follows> followings;

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

    public String getActiveString() {
        return this.getIsActive() ? "public" : "private";
    }

    public void likePaint(Paint paint) {
        if (likes == null) {
            likes = new LinkedList<>();
        }
        likes.add(new Likes(paint));
    }

    public void addFollowing(User user) {
        if (followings == null) {
            followings = new LinkedList<>();
        }
        followings.add(new Follows(user));
    }
}
