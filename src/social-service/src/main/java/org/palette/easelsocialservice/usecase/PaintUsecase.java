package org.palette.easelsocialservice.usecase;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.persistence.domain.Link;
import org.palette.easelsocialservice.persistence.domain.Media;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.service.LinkService;
import org.palette.easelsocialservice.service.MediaService;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        paintService.bindUserWithPaint(user, paint);

        paintCreateRequest.inReplyToPaintId().ifPresent(inReplyToPaint -> paintService.bindReplyPaint(paint, inReplyToPaint));
        paintCreateRequest.quotePaintId().ifPresent(quotePaintId -> paintService.bindQuotePaint(paint, quotePaintId));

        paintCreateRequest.mentions().ifPresent(mentions -> {
            List<Long> uids = mentions.stream().map(MentionRequest::userId).distinct().toList();

            userService.checkUserExists(uids);
            Map<Long, User> users = userService.getUserMapByUids(uids);
            paintService.createMentions(paint, mentions, users);
        });

        paintCreateRequest.taggedUserIds().ifPresent(taggedUserIds -> {
            userService.checkUserExists(taggedUserIds);
            List<User> users = userService.getUsersByUids(taggedUserIds);
            paintService.createTaggedUsers(paint, users);
        });

        paintCreateRequest.hashtags().ifPresent(hashtags -> paintService.bindHashtagsWithPaint(paint, hashtags));

        paintCreateRequest.links().ifPresent(links -> {
            List<Link> createdLinks = linkService.createLinks(links);
            paintService.bindLinksWithPaint(paint, links, createdLinks);
        });

        paintCreateRequest.medias().ifPresent(medias -> {
            List<Media> createdMedias = mediaService.createMedias(medias);
            paintService.bindMediaWithPaint(paint, createdMedias);
        });

        paintService.createPaint(paint);

        return new PaintCreateResponse(paint.getPid());
    }

    public void repaint(Long userId, RepaintRequest repaintRequest) {
        User user = userService.getUser(userId);
        paintService.bindRepaintWithPaint(user, repaintRequest);
    }

    public PaintResponse getSinglePaint(Long userId, Long paintId) {
        // TODO: compare userId for like, repaint, mark
        return paintService.getPaintById(userId, paintId);
    }

    public List<PaintResponse> getSingleBefore(Long userId, Long paintId) {
        // TODO: compasre userId for like, repaint, mark
        return paintService.getPaintBeforeById(userId, paintId);
    }
}
