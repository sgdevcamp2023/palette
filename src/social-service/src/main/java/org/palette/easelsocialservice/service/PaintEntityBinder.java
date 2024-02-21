package org.palette.easelsocialservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.dto.request.LinkRequest;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaintEntityBinder {

    public static void bindUserWithPaint(User user, Paint paint) {
        paint.setAuthor(user);
    }

    public static void bindMentions(Paint paint, List<MentionRequest> mentions, Map<Long, User> users) {
        List<Mentions> mentionRelations = new LinkedList<>();
        for (MentionRequest mention : mentions) {
            mentionRelations.add(new Mentions(users.get(mention.userId()), mention.start(), mention.end()));
        }
        paint.addAllMentions(mentionRelations);
    }

    public static void bindTaggedUsers(Paint paint, List<User> users) {
        List<TagsUser> tagsUsers = new LinkedList<>();
        for (User user : users) {
            tagsUsers.add(new TagsUser(user));
        }
        paint.addAllTaggedUsers(tagsUsers);
    }

    public static void bindHashtagsWithPaint(Paint paint, List<HashtagRequest> hashtags) {
        List<Tags> tags = new LinkedList<>();
        for (HashtagRequest hashtag : hashtags) {
            Tags tag = new Tags(new Hashtag(hashtag.tag()), hashtag.start(), hashtag.end());
            tags.add(tag);
        }
        paint.addAllHashtags(tags);
    }

    public static void bindLinksWithPaint(Paint paint, List<LinkRequest> linkRequests, List<Link> links) {
        List<Contains> contains = new LinkedList<>();
        for (int i = 0; i < linkRequests.size(); i++) {
            LinkRequest request = linkRequests.get(i);
            contains.add(new Contains(links.get(i), request.start(), request.end()));
        }
        paint.addAllLinks(contains);
    }

    public static void bindMediaWithPaint(Paint paint, List<Media> medias) {
        List<Uses> usings = new LinkedList<>();
        for (Media media : medias) {
            usings.add(new Uses(media));
        }
        paint.addAllMedia(usings);
    }

    public static void bindReplyPaint(Paint paint, Paint inReplyPaint) {
        paint.setInReplyToPaint(inReplyPaint);
    }

    public static void bindQuotePaint(Paint paint, Paint quotePaint) {
        paint.addQuotePaint(quotePaint);
    }
}
