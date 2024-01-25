package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.dto.request.LinkRequest;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.exception.BaseException;
import org.palette.easelsocialservice.exception.ExceptionType;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;

    public void createPaint(Paint paint) {
        paintRepository.save(paint);
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
        List<Uses> usings = new LinkedList<>();
        for (Media media : medias) {
            usings.add(new Uses(media));
        }
        paint.addAllMedia(usings);
        paintRepository.save(paint);
    }

    public void bindReplyPaint(Paint paint, Long inReplyToPaint) {
        Paint inReplyPaint = paintRepository.findById(inReplyToPaint)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.addInReplyToPaint(inReplyPaint);
        paintRepository.save(paint);
    }

    public void bindQuotePaint(Paint paint, Long quotePaintId) {
        Paint quotePaint = paintRepository.findById(quotePaintId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.addQuotePaint(quotePaint);
        paintRepository.save(paint);
    }

    public void bindRepaintWithPaint(User user, RepaintRequest repaintRequest) {
        Paint paint = paintRepository.findById(repaintRequest.originPaintId())
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.addRepaint(user);
        paintRepository.save(paint);
    }

    public Paint getPaintById(Long userId, Long paintId) {
        return paintRepository.findByPid(paintId).get();
    }
}
