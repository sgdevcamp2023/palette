package org.palette.easelsocialservice.usecase;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PaintUsecase {
    private final PaintService paintService;
    private final UserService userService;
    private final HashtagService hashtagService;
    private final LinkService linkService;
    private final MediaService mediaService;

    @Transactional
    public PaintCreateResponse createPaint(Long userId, PaintCreateRequest paintCreateRequest) {
        Paint paint = paintService.createPaint(paintCreateRequest.text(), paintCreateRequest.inReplyToPaintId(), paintCreateRequest.quotePaintId());

        User user = userService.getUser(userId);
        paintService.bindUserWithPaint(user, paint);

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

        paintCreateRequest.hashtags().ifPresent(hashtags -> {
            paintService.bindHashtagsWithPaint(paint, hashtags);
        });

        paintCreateRequest.links().ifPresent(links -> {
            List<Link> createdLinks = linkService.createLinks(links);
            paintService.bindLinksWithPaint(paint, createdLinks);
        });

        paintCreateRequest.medias().ifPresent(medias -> {
            List<Media> createdMedias = mediaService.createMedias(medias);
            paintService.bindMediaWithPaint(paint, createdMedias);
        });

        return new PaintCreateResponse(paint.getPid());
    }
}
