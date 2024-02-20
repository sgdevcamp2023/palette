package org.palette.easelsearchservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.detail.*;
import org.palette.easelsearchservice.dto.response.*;
import org.palette.grpc.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaintEntityConverter {
    public PaintResponse getQuotePaint(GPaintResponse paint) {
//        return Optional.ofNullable(paint.getQuote())
//                .map(qp -> convertToQuotePaintResponse(qp)
//                .orElse(null);
        return null;
    }

    public PaintResponse convertToPaintResponse(GPaintResponse paint) {
        PaintResponse quotePaint = getQuotePaint(paint);
        Entities entities = convertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByGPaintResponse(paint, quotePaint, entities, includes);
    }

    public PaintResponse convertToQuotePaintResponse(GPaintResponse paint) {
        Entities entities = convertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByGPaintResponse(paint, entities, includes);
    }

    public List<PaintResponse> convertToPaintResponse(final List<GPaintResponse> paints) {
        return paints.stream()
                .map(this::convertToPaintResponse)
                .toList();
    }

    public Entities convertToEntities(GPaintResponse paint) {
        List<HashtagResponse> hashtags = convertToHashtagResponse(paint.getHashtagsList());
        List<MentionResponse> mentions = convertToMentionResponse(paint.getMentionsList());

        return new Entities(hashtags, mentions);
    }

    public Includes convertToIncludes(GPaintResponse paint) {
        List<MediaResponse> medias = convertToMediaResponse(paint.getMediasList());
        List<UserResponse> taggedUsers = convertToUserResponses(paint.getTaggedUsersList());
        List<LinkResponse> links = convertToLinkResponses(paint.getLinksList());

        return new Includes(medias, taggedUsers, links);
    }

    public List<HashtagResponse> convertToHashtagResponse(List<GHashtagResponse> hashtags) {
        return hashtags
                .stream()
                .map(HashtagResponse::from)
                .toList();
    }

    public List<MentionResponse> convertToMentionResponse(List<GMentionResponse> mentions) {
        return mentions
                .stream()
                .map(MentionResponse::from)
                .toList();
    }

    public List<MediaResponse> convertToMediaResponse(List<GMediaResponse> medias) {
        return medias
                .stream()
                .map(MediaResponse::from)
                .toList();
    }

    public List<UserResponse> convertToUserResponses(List<GUserResponse> taggedUsers) {
        return taggedUsers
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public List<LinkResponse> convertToLinkResponses(List<GLinkResponse> links) {
        return links
                .stream()
                .map(LinkResponse::from)
                .toList();
    }
}
