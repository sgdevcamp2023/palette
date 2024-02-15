package org.palette.easelsocialservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.palette.grpc.*;

import java.util.ArrayList;
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
    public void loadUserFollowInformation(
            final GLoadUserFollowInformationRequest request,
            final StreamObserver<GLoadUserFollowInformationResponse> responseObserver
    ) {
        final long followingCount = userService.getFollowingCount(request.getPassport().getId());
        final long followerCount = userService.getFollowerCount(request.getPassport().getId());

        GLoadUserFollowInformationResponse response = GLoadUserFollowInformationResponse.newBuilder()
                .setFollowerCount(followingCount)
                .setFollowingCount(followerCount)
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
        List<GPaintResponse> result = new ArrayList<>();
        List<GHashtagResponse> hashtags = new ArrayList<>();
        hashtags.add(GHashtagResponse.newBuilder()
                .setStart(0)
                .setEnd(2)
                .setTag("tag")
                .build());

        List<GMentionResponse> mentions = new ArrayList<>();
        mentions.add(GMentionResponse.newBuilder()
                .setStart(0)
                .setEnd(2)
                .setUserId(3L)
                .setMention("mention")
                .build());

        List<GUserResponse> taggedUsers = new ArrayList<>();
        taggedUsers.add(GUserResponse.newBuilder()
                .setId(1L)
                .setUsername("username")
                .setNickname("nickname")
                .setImagePath("imagePath")
                .setStatus("status")
                .build());

        List<GMediaResponse> medias = new ArrayList<>();
        medias.add(GMediaResponse.newBuilder()
                .setType("type")
                .setPath("path")
                .build());

        List<GLinkResponse> links = new ArrayList<>();
        links.add(GLinkResponse.newBuilder()
                .setStart(0)
                .setEnd(2)
                .setShortLink("shortLink")
                .setOriginalLink("originalLink")
                .build());

        result.add(GPaintResponse.newBuilder()
                .setPid(10L)
                .setIsReply(false)
                .setAuthorId(1L)
                .setAuthorUsername("username")
                .setAuthorNickname("nickname")
                .setAuthorImagePath("imagePath")
                .setAuthorStatus("status")
                .setQuote(GPaintResponse.newBuilder()
                        .setPid(11L)
                        .build())
                .setCreatedAt("2024-02-15")
                .setText("text")
                .addAllHashtags(hashtags)
                .addAllMentions(mentions)
                .addAllTaggedUsers(taggedUsers)
                .addAllMedias(medias)
                .addAllLinks(links)
                .build());
        return result;
    }

    private User convertToUser(final GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }
}
