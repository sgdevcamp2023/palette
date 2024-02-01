package org.palette.easelsocialservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.response.*;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.PaintMetrics;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.stereotype.Component;

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
        Entities entities = covertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByPaint(paint, quotePaint, entities, includes, paintMetrics);
    }

    public PaintResponse convertToQuotePaintResponse(Paint paint) {
        Entities entities = covertToEntities(paint);
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

    public Entities covertToEntities(Paint paint) {
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
}
