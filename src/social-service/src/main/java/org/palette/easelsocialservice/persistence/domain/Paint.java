package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Node
public class Paint {
    @Id
    @GeneratedValue(PaintIdGenerator.class)
    private Long pid;

    @Relationship(type = "CREATES", direction = Relationship.Direction.INCOMING)
    private Creates author;

    @Relationship(type = "REPLIES")
    private Replies inReplyToPaint;

    @Relationship(type = "QUOTES")
    private Quotes quotePaint;

    @Relationship(type = "MENTIONS")
    private List<Mentions> mentions;

    @Relationship(type = "TAGS_USER")
    private List<TagsUser> taggedUsers;

    @Relationship(type = "CONTAINS")
    private List<Contains> links;

    @Relationship(type = "TAGS")
    private List<Tags> hashtags;

    @Relationship(type = "USES")
    private List<Uses> medias;

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

    public void addHashtag(Hashtag hashtag, int startIdx, int endIdx) {
        if (this.hashtags == null) {
            this.hashtags = new LinkedList<>();
        }
        this.hashtags.add(new Tags(hashtag, startIdx, endIdx));
    }

    public void addMedia(Media media) {
        if (this.medias == null) {
            this.medias = new LinkedList<>();
        }
        this.medias.add(new Uses(media));
    }

    public void addInReplyToPaint(Paint paint) {
        this.inReplyToPaint = new Replies(paint);
    }

    public void addQuotePaint(Paint paint) {
        this.quotePaint = new Quotes(paint);
    }

    public void addTaggedUser(User user) {
        if (this.taggedUsers == null) {
            this.taggedUsers = new LinkedList<>();
        }
        this.taggedUsers.add(new TagsUser(user));
    }

    public void addAllLinks(List<Contains> contains) {
        this.links = contains;
    }

    public void addAllMentions(List<Mentions> mentions) {
        this.mentions = mentions;
    }

    public void addAllTaggedUsers(List<TagsUser> tagsUsers) {
        this.taggedUsers = tagsUsers;
    }
}
