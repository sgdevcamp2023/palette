package org.palette.easelsocialservice.usecase;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.dto.response.ThreadResponse;
import org.palette.easelsocialservice.persistence.domain.Link;
import org.palette.easelsocialservice.persistence.domain.Media;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaintUsecase {
    private final PaintService paintService;
    private final UserService userService;
    private final LinkService linkService;
    private final MediaService mediaService;

    @Transactional
    public PaintCreateResponse createPaint(Long userId, PaintCreateRequest paintCreateRequest) {
        Paint paint = new Paint(paintCreateRequest.text());

        User user = userService.getUser(userId);
        PaintEntityBinder.bindUserWithPaint(user, paint);

        Optional.ofNullable(paintCreateRequest.quotePaintId())
                .ifPresent(quotePaintId -> {
                    Paint quotePaint = paintService.getPaintById(quotePaintId);
                    PaintEntityBinder.bindQuotePaint(paint, quotePaint);
                });

        Optional.ofNullable(paintCreateRequest.inReplyToPaintId())
                .ifPresent(inReplyToPaintId -> {
                    Paint inReplyToPaint = paintService.getPaintById(inReplyToPaintId);
                    PaintEntityBinder.bindReplyPaint(paint, inReplyToPaint);
                });

        Optional.ofNullable(paintCreateRequest.mentions())
                .ifPresent(mentions -> {
                    List<Long> uids = mentions.stream().map(MentionRequest::userId).distinct().toList();
                    userService.checkUserExists(uids);
                    Map<Long, User> users = userService.getUserMapByUids(uids);
                    PaintEntityBinder.bindMentions(paint, mentions, users);
                });

        Optional.ofNullable(paintCreateRequest.taggedUserIds())
                .ifPresent(taggedUserIds -> {
                    userService.checkUserExists(taggedUserIds);
                    List<User> users = userService.getUsersByUids(taggedUserIds);
                    PaintEntityBinder.bindTaggedUsers(paint, users);
                });

        Optional.ofNullable(paintCreateRequest.hashtags())
                .ifPresent(hashtags -> PaintEntityBinder.bindHashtagsWithPaint(paint, hashtags));

        Optional.ofNullable(paintCreateRequest.links())
                .ifPresent(links -> {
                    List<Link> createdLinks = linkService.createLinks(links);
                    PaintEntityBinder.bindLinksWithPaint(paint, links, createdLinks);
                });

        Optional.ofNullable(paintCreateRequest.medias())
                .ifPresent(medias -> {
                    List<Media> createdMedias = mediaService.createMedias(medias);
                    PaintEntityBinder.bindMediaWithPaint(paint, createdMedias);
                });

        paintService.createPaint(paint);

        return new PaintCreateResponse(paint.getPid());
    }

    public void repaint(Long userId, RepaintRequest repaintRequest) {
        User user = userService.getUser(userId);
        paintService.repaintWithPaint(user, repaintRequest);
    }

    public PaintResponse getSinglePaint(Long userId, Long paintId) {
        PaintResponse paintResponse = paintService.getPaintById(userId, paintId);
        paintService.viewSinglePaint(paintId);
        return paintResponse;
    }

    public List<PaintResponse> getSingleBefore(Long userId, Long paintId) {
        return paintService.getPaintBeforeById(userId, paintId);
    }

    public List<ThreadResponse> getSingleAfter(Long userId, Long paintId) {
        return paintService.getPaintAfterById(userId, paintId);
    }
}
