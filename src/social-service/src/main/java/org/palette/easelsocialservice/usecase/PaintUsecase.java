package org.palette.easelsocialservice.usecase;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.service.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

        paintService.createMentions(paint, paintCreateRequest.mentions());

        paintService.createTaggedUsers(paint, paintCreateRequest.taggedUserIds());

        List<Hashtag> hashtags = hashtagService.createHashtags(paintCreateRequest.hashtags());
        paintService.bindHashtagsWithPaint(paint, hashtags);

        List<Link> links = linkService.createLinks(paintCreateRequest.links());
        paintService.bindLinksWithPaint(paint, links);

        List<Media> medias = mediaService.createMedias(paintCreateRequest.medias());
        paintService.bindMediaWithPaint(paint, medias);

        return new PaintCreateResponse(paint.getPid());
    }
}
