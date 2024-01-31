package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Relationship(type = "REPAINTS", direction = Relationship.Direction.INCOMING)
    private List<Repaints> repaints;

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

    private boolean hasQuote;

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

    public void setInReplyToPaint(Paint paint) {
        this.inReplyToPaint = new Replies(paint);
    }

    public void addQuotePaint(Paint paint) {
        this.hasQuote = true;
        this.quotePaint = new Quotes(paint);
    }

    public void addRepaint(User user) {
        if (this.repaints == null) {
            this.repaints = new LinkedList<>();
        }
        this.repaints.add(new Repaints(user));
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

    public void addAllHashtags(List<Tags> tags) {
        this.hashtags = tags;
    }

    public void addAllMedia(List<Uses> medias) {
        this.medias = medias;
    }
}
