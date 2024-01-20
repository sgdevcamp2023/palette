package org.palette.easelsocialservice.usecase;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.dto.response.PaintCreateResponse;
import org.palette.easelsocialservice.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaintUsecase {
    private final PaintService paintService;
    private final UserService userService;
    private final HashtagService hashtagService;
    private final LinkService linkService;
    private final MediaService mediaService;

    public PaintCreateResponse createPaint(Long userId, PaintCreateRequest paintCreateRequest) {
        Long paintId = paintService.createPaint(userId, paintCreateRequest.text(), paintCreateRequest.inReplyToPaintId(), paintCreateRequest.quotePaintId());
        userService.bindUserWithPost(userId, paintId);
        userService.createMentions(paintId, paintCreateRequest.mentions());
        userService.createTaggedUsers(paintId, paintCreateRequest.taggedUserIds());
        hashtagService.createHashtags(paintId, paintCreateRequest.hashtags());
        linkService.createLinks(paintId, paintCreateRequest.links());
        mediaService.createMedias(paintId, paintCreateRequest.medias());

        return new PaintCreateResponse(paintId);
    }
}
