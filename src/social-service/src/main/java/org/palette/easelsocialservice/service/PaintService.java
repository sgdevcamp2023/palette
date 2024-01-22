package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.persistence.*;
import org.palette.easelsocialservice.persistence.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        for (MentionRequest mention: mentions) {
            paint.addMention(users.get(mention.userId()), mention.start(), mention.end());
        }
        paintRepository.save(paint);
    }

    public void createTaggedUsers(Paint paint, List<User> users) {
        for (User user: users) {
            paint.addTaggedUser(user);
        }
        paintRepository.save(paint);
    }

    public void bindHashtagsWithPaint(Paint paint, List<HashtagRequest> hashtags) {
        for (HashtagRequest hashtag: hashtags) {
            paint.addHashtag(new Hashtag(hashtag.tag()), hashtag.start(), hashtag.end());
        }
        paintRepository.save(paint);
    }

    public void bindLinksWithPaint(Paint paint, List<Link> links) {
    }

    public void bindMediaWithPaint(Paint paint, List<Media> medias) {
        for (Media media : medias) {
            paint.addMedia(media);
        }
        paintRepository.save(paint);
    }
}
