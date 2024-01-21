package org.palette.easelsocialservice.usecase;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PaintUsecase {
    private final PaintService paintService;
    private final UserService userService;
    private final HashtagService hashtagService;
    private final LinkService linkService;
    private final MediaService mediaService;

    public PaintCreateResponse createPaint(Long userId, PaintCreateRequest paintCreateRequest) {
        Paint paint = paintService.createPaint(paintCreateRequest.text(), paintCreateRequest.inReplyToPaintId(), paintCreateRequest.quotePaintId());

        User user = userService.getUser(userId);
        paintService.bindUserWithPaint(user, paint);

        paintCreateRequest.mentions().ifPresent(
                mentions -> {
                    List<Long> uids = mentions.stream()
                            .map(MentionRequest::userId)
                            .toList();

                    userService.checkUserExists(uids);
                    paintService.createMentions(paint, mentions);
                }
        );

        paintCreateRequest.taggedUserIds().ifPresent(
                taggedUserIds -> {
                    userService.checkUserExists(taggedUserIds);
                    paintService.createTaggedUsers(paint, taggedUserIds);
                }
        );

        paintCreateRequest.hashtags().ifPresent(hashtags -> {
            List<Hashtag> createdHashtags = hashtagService.createHashtags(hashtags);
            paintService.bindHashtagsWithPaint(paint, createdHashtags);
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
