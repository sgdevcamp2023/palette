package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.dto.request.LinkRequest;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.persistence.relationship.Contains;
import org.palette.easelsocialservice.persistence.relationship.Mentions;
import org.palette.easelsocialservice.persistence.relationship.Tags;
import org.palette.easelsocialservice.persistence.relationship.TagsUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;

    public Paint createPaint(String text, Optional<Long> inReplyToPaintId, Optional<Long> quotePaintId) {
        // TODO: 예외처리
        Paint paint = new Paint(text);

        inReplyToPaintId.map(paintRepository::findByPid)
                .ifPresent(paintOpt -> paintOpt.ifPresentOrElse(
                        paint::addInReplyToPaint,
                        () -> { throw new NoSuchElementException("답장할 Paint를 찾을 수 없습니다. ID: " + inReplyToPaintId.get()); }
                ));

        quotePaintId.map(paintRepository::findByPid)
                .ifPresent(paintOpt -> paintOpt.ifPresentOrElse(
                        paint::addQuotePaint,
                        () -> { throw new NoSuchElementException("인용할 Paint를 찾을 수 없습니다. ID: " + quotePaintId.get()); }
                ));

        return paintRepository.save(paint);
    }

    public void bindUserWithPaint(User user, Paint paint) {
        paint.setAuthor(user);
        paintRepository.save(paint);
    }

    public void createMentions(Paint paint, List<MentionRequest> mentions, Map<Long, User> users) {
        List<Mentions> mentionRelations = new LinkedList<>();
        for (MentionRequest mention: mentions) {
            mentionRelations.add(new Mentions(users.get(mention.userId()), mention.start(), mention.end()));
        }
        paint.addAllMentions(mentionRelations);
        paintRepository.save(paint);
    }

    public void createTaggedUsers(Paint paint, List<User> users) {
        List<TagsUser> tagsUsers = new LinkedList<>();
        for (User user: users) {
            tagsUsers.add(new TagsUser(user));
        }
        paint.addAllTaggedUsers(tagsUsers);
        paintRepository.save(paint);
    }

    public void bindHashtagsWithPaint(Paint paint, List<HashtagRequest> hashtags) {
        List<Tags> tags = new LinkedList<>();
        for (HashtagRequest hashtag: hashtags) {
            Tags tag = new Tags(new Hashtag(hashtag.tag()), hashtag.start(), hashtag.end());
            tags.add(tag);
        }
        paint.addAllHashtags(tags);
        paintRepository.save(paint);
    }

    public void bindLinksWithPaint(Paint paint, List<LinkRequest> linkRequests, List<Link> links) {
        List<Contains> contains = new LinkedList<>();
        for (int i = 0; i < linkRequests.size(); i++) {
            LinkRequest request = linkRequests.get(i);
            contains.add(new Contains(links.get(i), request.start(), request.end()));
        }
        paint.addAllLinks(contains);
        paintRepository.save(paint);
    }

    public void bindMediaWithPaint(Paint paint, List<Media> medias) {
        for (Media media : medias) {
            paint.addMedia(media);
        }
        paintRepository.save(paint);
    }
}
