package org.palette.easelsocialservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.detail.*;
import org.palette.easelsocialservice.dto.response.*;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.PaintMetrics;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaintEntityConverter {
    private final PaintRepository paintRepository;

    public PaintResponse getQuotePaint(Paint paint) {
        return Optional.ofNullable(paint.getQuotePaint())
                .map(qp -> convertToQuotePaintResponse(qp.getPaint()))
                .orElse(null);
    }

    public PaintResponse convertToPaintResponse(Paint paint, PaintMetrics paintMetrics) {
        PaintResponse quotePaint = getQuotePaint(paint);
        Entities entities = convertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByPaint(paint, quotePaint, entities, includes, paintMetrics);
    }

    public PaintResponse convertToPaintResponse(Paint paint) {
        PaintResponse quotePaint = getQuotePaint(paint);
        Entities entities = convertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByPaint(paint, quotePaint, entities, includes);
    }

    public PaintResponse convertToQuotePaintResponse(Paint paint) {
        Entities entities = convertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByPaint(paint, entities, includes);
    }

    public List<PaintResponse> convertToPaintResponse(Long userId, List<Paint> paints) {
        return paints.stream()
                .map(paint -> {
                    PaintMetrics metrics = paintRepository.findMetricsByPidAndUid(userId, paint.getPid());
                    return convertToPaintResponse(paint, metrics);
                })
                .toList();
    }

    public List<PaintResponse> convertToPaintResponse(final List<Paint> paints) {
        return paints.stream()
                .map(paint -> {
                    return convertToPaintResponse(paint);
                })
                .toList();
    }

    public Entities convertToEntities(Paint paint) {
        List<HashtagResponse> hashtags = convertToHashtagResponse(paint.getHashtags());
        List<MentionResponse> mentions = convertToMentionResponse(paint.getMentions());

        return new Entities(hashtags, mentions);
    }

    public Includes convertToIncludes(Paint paint) {
        List<MediaResponse> medias = convertToMediaResponse(paint.getMedias());
        List<UserResponse> taggedUsers = convertToUserResponses(paint.getTaggedUsers());
        List<LinkResponse> links = convertToLinkResponses(paint.getLinks());

        return new Includes(medias, taggedUsers, links);
    }

    public List<HashtagResponse> convertToHashtagResponse(List<Tags> hashtags) {
        return hashtags
                .stream()
                .map(HashtagResponse::from)
                .toList();
    }

    public List<MentionResponse> convertToMentionResponse(List<Mentions> mentions) {
        return mentions
                .stream()
                .map(MentionResponse::from)
                .toList();
    }

    public List<MediaResponse> convertToMediaResponse(List<Uses> medias) {
        return medias
                .stream()
                .map(MediaResponse::from)
                .toList();
    }

    public List<UserResponse> convertToUserResponses(List<TagsUser> taggedUsers) {
        return taggedUsers
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public List<LinkResponse> convertToLinkResponses(List<Contains> links) {
        return links
                .stream()
                .map(LinkResponse::from)
                .toList();
    }

    public static PaintCreatedEvent convertToPainCreatedEvent(Paint paint, boolean isQuotedPaint) {
        boolean isReply = paint.getInReplyToPaint() == null;
        User author = paint.getAuthor().getUser();
        PaintCreatedEvent quotePaint = isQuotedPaint || paint.getQuotePaint() == null ? null : convertToPainCreatedEvent(paint.getQuotePaint().getPaint(), true);
        List<HashtagRecord> hashtagRecords = convertToHashtagRecord(paint.getHashtags());
        List<MentionRecord> mentionRecords = convertToMentionRecord(paint.getMentions());
        List<UserRecord> taggedUserRecords  = convertToUserRecord(paint.getTaggedUsers());
        List<MediaRecord> mediaRecords = convertToMediaRecord(paint.getMedias());
        List<LinkRecord> linkRecords = convertToLinkRecord(paint.getLinks());

        return new PaintCreatedEvent(
                paint.getPid(),
                isReply,
                author.getUid(),
                author.getUsername(),
                author.getNickname(),
                author.getImagePath(),
                author.getActiveString(),
                quotePaint,
                paint.getCreatedAt(),
                paint.getContent(),
                hashtagRecords,
                mentionRecords,
                taggedUserRecords,
                mediaRecords,
                linkRecords
                );
    }

    private static List<LinkRecord> convertToLinkRecord(final List<Contains> links) {
        if (links == null) {
            return Collections.emptyList();
        }

        return links.stream()
                .map(media -> new LinkRecord(
                                media.getStart(),
                                media.getEnd(),
                                media.getLink().getShortLink(),
                                media.getLink().getOriginalLink()
                        )
                ).toList();
    }

    private static List<MediaRecord> convertToMediaRecord(final List<Uses> medias) {
        if (medias == null) {
            return Collections.emptyList();
        }

        return medias.stream()
                .map(media -> new MediaRecord(
                        media.getMedia().getType(),
                        media.getMedia().getPath())
                ).toList();
    }

    private static List<UserRecord> convertToUserRecord(final List<TagsUser> taggedUsers) {
        if (taggedUsers == null) {
            return Collections.emptyList();
        }

        return taggedUsers.stream()
                .map(taggedUser -> new UserRecord(
                                taggedUser.getUser().getUid(),
                                taggedUser.getUser().getUsername(),
                                taggedUser.getUser().getNickname(),
                                taggedUser.getUser().getImagePath(),
                                taggedUser.getUser().getActiveString()
                        )
                ).toList();
    }

    private static List<MentionRecord> convertToMentionRecord(final List<Mentions> mentions) {
        if (mentions == null) {
            return Collections.emptyList();
        }

        return mentions.stream()
                .map(mention -> new MentionRecord(
                        mention.getStart(),
                        mention.getEnd(),
                        mention.getUser().getUid(),
                        mention.getUser().getUsername()
                )).toList();
    }

    private static List<HashtagRecord> convertToHashtagRecord(final List<Tags> hashtags) {
        if (hashtags == null) {
            return Collections.emptyList();
        }

        return hashtags.stream()
                .map(tags -> new HashtagRecord(
                        tags.getStart(),
                        tags.getEnd(),
                        tags.getHashtag().getTag())
                ).toList();
    }
}
