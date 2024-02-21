package org.palette.easelsocialservice.external.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.dto.response.*;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.palette.grpc.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GSocialServiceGrpc.GSocialServiceImplBase {
    private final PaintService paintService;
    private final UserService userService;

    @Override
    public void createUser(
            final GCreateUserRequest request,
            final StreamObserver<GCreateUserResponse> responseStreamObserver) {
        userService.createUser(convertToUser(request));
        GCreateUserResponse response = GCreateUserResponse.newBuilder().setIsSuccess(true).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void getFollowerIds(
            final GFollowerIdsRequest request,
            final StreamObserver<GFollowerIdsResponse> responseObserver
    ) {
        List<Long> followerIds = userService.getFollowerIds(request.getUserId());
        GFollowerIdsResponse response = GFollowerIdsResponse.newBuilder()
                .addAllFollowerIds(followerIds)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void loadUserFollowInformation(
            final GLoadUserFollowInformationRequest request,
            final StreamObserver<GLoadUserFollowInformationResponse> responseObserver
    ) {
        final long followingCount = userService.getFollowingCount(request.getPassport().getId());
        final long followerCount = userService.getFollowerCount(request.getPassport().getId());

        GLoadUserFollowInformationResponse response = GLoadUserFollowInformationResponse.newBuilder()
                .setFollowerCount(followerCount)
                .setFollowingCount(followingCount)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPaintsByIds(
            final GGetPaintsByIdsRequest request,
            final StreamObserver<GGetPaintsByIdsResponse> responseObserver
    ) {
        List<PaintResponse> paints = paintService.getAllPaintsByPid(request.getPaintIdsList());
        List<GPaintResponse> gpaints = convertToGPaintResponses(paints);

        GGetPaintsByIdsResponse response = GGetPaintsByIdsResponse.newBuilder()
                .addAllPaints(gpaints)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<GPaintResponse> convertToGPaintResponses(final List<PaintResponse> paints) {
        return paints.stream()
                .map(p -> convertToGPaintResponse(p, false))
                .toList();
    }

    private GPaintResponse convertToGPaintResponse(final PaintResponse paint, final boolean isQuotePaint) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return GPaintResponse.newBuilder()
                .setPid(paint.id())
                .setIsReply(paint.isReply())
                .setAuthorId(paint.authorId())
                .setAuthorUsername(paint.authorUsername())
                .setAuthorNickname(paint.authorNickname())
                .setAuthorImagePath(paint.authorImagePath())
                .setAuthorStatus(paint.authorStatus())
                .setQuote(isQuotePaint || paint.quotePaint() == null? GPaintResponse.getDefaultInstance() : convertToGPaintResponse(paint.quotePaint(), true))
                .setCreatedAt(paint.createdAt().format(formatter))
                .addAllHashtags(convertToGHashtagResponse(paint.entities().hashtags()))
                .addAllMentions(convertToGMentionResponse(paint.entities().mentions()))
                .addAllTaggedUsers(convertToGUserResponse(paint.includes().users()))
                .addAllMedias(convertToGMediaResponse(paint.includes().medias()))
                .addAllLinks(convertToGLinkResponse(paint.includes().links()))
                .setText(paint.text())
                .build();
    }

    private List<GHashtagResponse> convertToGHashtagResponse(final List<HashtagResponse> hashtags) {
        if (hashtags == null) {
            return Collections.emptyList();
        }

        return hashtags.stream()
                .map(h -> GHashtagResponse.newBuilder()
                        .setStart(h.start())
                        .setEnd(h.end())
                        .setTag(h.tag())
                        .build())
                .toList();
    }

    private List<GMentionResponse> convertToGMentionResponse(final List<MentionResponse> mentions) {
        if (mentions == null) {
            return Collections.emptyList();
        }

        return mentions.stream()
                .map(m -> GMentionResponse.newBuilder()
                        .setStart(m.start())
                        .setEnd(m.end())
                        .setUserId(m.userId())
                        .setMention(m.mention())
                        .build())
                .toList();
    }

    private List<GUserResponse> convertToGUserResponse(final List<UserResponse> taggedUsers) {
        if (taggedUsers == null) {
            return Collections.emptyList();
        }

        return taggedUsers.stream()
                .map(tu -> GUserResponse.newBuilder()
                        .setId(tu.id())
                        .setUsername(tu.username())
                        .setNickname(tu.nickname())
                        .setImagePath(tu.imagePath())
                        .setStatus(tu.status())
                        .build())
                .toList();
    }

    private List<GMediaResponse> convertToGMediaResponse(final List<MediaResponse> medias) {
        if (medias == null) {
            return Collections.emptyList();
        }

        return medias.stream()
                .map(m -> GMediaResponse.newBuilder()
                        .setType("image")
                        .setPath(m.path())
                        .build())
                .toList();
    }

    private List<GLinkResponse> convertToGLinkResponse(final List<LinkResponse> links) {
        if (links == null) {
            return Collections.emptyList();
        }

        return links.stream()
                .map(l -> GLinkResponse.newBuilder()
                        .setStart(l.start())
                        .setEnd(l.end())
                        .setShortLink(l.shortLink())
                        .setOriginalLink(l.originalLink())
                        .build())
                .toList();
    }

    private User convertToUser(final GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }
}
