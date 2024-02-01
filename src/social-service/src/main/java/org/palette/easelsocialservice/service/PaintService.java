package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.HashtagRequest;
import org.palette.easelsocialservice.dto.request.LinkRequest;
import org.palette.easelsocialservice.dto.request.MentionRequest;
import org.palette.easelsocialservice.dto.request.RepaintRequest;
import org.palette.easelsocialservice.dto.response.*;
import org.palette.easelsocialservice.exception.BaseException;
import org.palette.easelsocialservice.exception.ExceptionType;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.domain.*;
import org.palette.easelsocialservice.persistence.relationship.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaintService {
    private final PaintRepository paintRepository;

    public void createPaint(Paint paint) {
        paintRepository.save(paint);
    }

    public void bindUserWithPaint(User user, Paint paint) {
        paint.setAuthor(user);
    }

    public void createMentions(Paint paint, List<MentionRequest> mentions, Map<Long, User> users) {
        List<Mentions> mentionRelations = new LinkedList<>();
        for (MentionRequest mention : mentions) {
            mentionRelations.add(new Mentions(users.get(mention.userId()), mention.start(), mention.end()));
        }
        paint.addAllMentions(mentionRelations);
    }

    public void createTaggedUsers(Paint paint, List<User> users) {
        List<TagsUser> tagsUsers = new LinkedList<>();
        for (User user : users) {
            tagsUsers.add(new TagsUser(user));
        }
        paint.addAllTaggedUsers(tagsUsers);
    }

    public void bindHashtagsWithPaint(Paint paint, List<HashtagRequest> hashtags) {
        List<Tags> tags = new LinkedList<>();
        for (HashtagRequest hashtag : hashtags) {
            Tags tag = new Tags(new Hashtag(hashtag.tag()), hashtag.start(), hashtag.end());
            tags.add(tag);
        }
        paint.addAllHashtags(tags);
    }

    public void bindLinksWithPaint(Paint paint, List<LinkRequest> linkRequests, List<Link> links) {
        List<Contains> contains = new LinkedList<>();
        for (int i = 0; i < linkRequests.size(); i++) {
            LinkRequest request = linkRequests.get(i);
            contains.add(new Contains(links.get(i), request.start(), request.end()));
        }
        paint.addAllLinks(contains);
    }

    public void bindMediaWithPaint(Paint paint, List<Media> medias) {
        List<Uses> usings = new LinkedList<>();
        for (Media media : medias) {
            usings.add(new Uses(media));
        }
        paint.addAllMedia(usings);
    }

    public void bindReplyPaint(Paint paint, Long inReplyToPaint) {
        Paint inReplyPaint = paintRepository.findById(inReplyToPaint)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.setInReplyToPaint(inReplyPaint);
    }

    public void bindQuotePaint(Paint paint, Long quotePaintId) {
        Paint quotePaint = paintRepository.findById(quotePaintId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.addQuotePaint(quotePaint);
    }

    public void bindRepaintWithPaint(User user, RepaintRequest repaintRequest) {
        Paint paint = paintRepository.findById(repaintRequest.originPaintId())
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.addRepaint(user);
    }

    // TODO: replyCount, likeCount, myLike, myRepaint, myMarked functions
    // first depth's getId()== relationship's id
    public PaintResponse getPaintById(Long userId, Long paintId) {
        Paint paint = paintRepository.findByPid(paintId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
        paint.updateView();
        paintRepository.save(paint);

        return convertToPaintResponse(paint);
    }

    public Paint getPaintById(Long paintId) {
        return paintRepository.findByPid(paintId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000002));
    }

    public List<PaintResponse> getPaintBeforeById(Long userId, Long paintId) {
        List<Paint> paints = distinctPaintsByPid(paintRepository.findAllBeforePaintByPid(paintId));

        return convertToPaintResponse(paints);
    }

    public List<ThreadResponse> getPaintAfterById(Long userId, Long paintId) {
        List<Paint> paints = distinctPaintsByPid(paintRepository.findAllAfterPaintByPid(paintId));

        List<ThreadResponse> threads = new LinkedList<>();
        int threadId = 0;
        for (Paint paint : paints) {
            checkAndSetQuotePaint(paint);
            List<Paint> subPaints = distinctPaintsByPid(paintRepository.findAllAfterPaintsByPid(paint.getPid()));

            threads.add(new ThreadResponse(threadId++, convertToPaintResponse(subPaints)));
        }

        return threads;
    }

    private void checkAndSetQuotePaint(Paint paint) {
        if (!paint.isHasQuote()) return;
        Paint quotePaint = paintRepository.findQuotePaintByPid(paint.getPid());
        paint.addQuotePaint(quotePaint);
    }

    private List<Paint> distinctPaintsByPid(List<Paint> paints) {
        return paints.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Paint::getPid))),
                        ArrayList::new
                ));
    }

    private PaintResponse convertToPaintResponse(Paint paint) {
        PaintResponse quotePaint = getQuotePaint(paint);
        Entities entities = covertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByPaint(paint, quotePaint, entities, includes);
    }

    private PaintResponse getQuotePaint(Paint paint) {
        return Optional.ofNullable(paint.getQuotePaint())
                .map(qp -> convertToQuotePaintResponse(qp.getPaint()))
                .orElse(null);
    }

    private PaintResponse convertToQuotePaintResponse(Paint paint) {
        Entities entities = covertToEntities(paint);
        Includes includes = convertToIncludes(paint);

        return PaintResponse.buildByPaint(paint, entities, includes);
    }

    private List<PaintResponse> convertToPaintResponse(List<Paint> paints) {
        return paints.stream()
                .map(this::convertToPaintResponse)
                .toList();
    }

    private Entities covertToEntities(Paint paint) {
        List<HashtagResponse> hashtags = convertToHashtagResponse(paint.getHashtags());
        List<MentionResponse> mentions = convertToMentionResponse(paint.getMentions());

        return new Entities(hashtags, mentions);
    }

    private Includes convertToIncludes(Paint paint) {
        List<MediaResponse> medias = convertToMediaResponse(paint.getMedias());
        List<UserResponse> taggedUsers = convertToUserResponses(paint.getTaggedUsers());
        List<LinkResponse> links = convertToLinkResponses(paint.getLinks());

        return new Includes(medias, taggedUsers, links);
    }

    private List<HashtagResponse> convertToHashtagResponse(List<Tags> hashtags) {
        return hashtags
                .stream()
                .map(HashtagResponse::from)
                .toList();
    }

    private List<MentionResponse> convertToMentionResponse(List<Mentions> mentions) {
        return mentions
                .stream()
                .map(MentionResponse::from)
                .toList();
    }

    private List<MediaResponse> convertToMediaResponse(List<Uses> medias) {
        return medias
                .stream()
                .map(MediaResponse::from)
                .toList();
    }

    private List<UserResponse> convertToUserResponses(List<TagsUser> taggedUsers) {
        return taggedUsers
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    private List<LinkResponse> convertToLinkResponses(List<Contains> links) {
        return links
                .stream()
                .map(LinkResponse::from)
                .toList();
    }
}
