package org.palette.easelsearchservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.dto.event.UserCreatedEvent;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.dto.response.UserResponse;
import org.palette.easelsearchservice.external.groc.GrpcSocialClient;
import org.palette.easelsearchservice.service.PaintEntityConverter;
import org.palette.easelsearchservice.service.SearchPaintService;
import org.palette.easelsearchservice.service.SearchUserService;
import org.palette.grpc.GPaintResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchUsecase {
    private final SearchPaintService searchPaintService;
    private final SearchUserService searchUserService;
    private final GrpcSocialClient grpcSocialClient;
    private final PaintEntityConverter paintEntityConverter;

    @Async
    public void paintCreated(PaintCreatedEvent paintCreatedEvent) {
        searchPaintService.createPaint(paintCreatedEvent);
    }

    @Async
    public void createUser(UserCreatedEvent userCreatedEvent) {
        searchUserService.createUser(userCreatedEvent);
    }

    @Async
    public void updateUser(UpdateUserEvent updateUserEvent) {
        searchUserService.updateUser(updateUserEvent);
    }

    public List<PaintResponse> searchAllPaints(final SearchRequest searchRequest) {
        List<Long> paintIds = searchPaintService.searchAllPaints(searchRequest);
        List<GPaintResponse> gPaints = grpcSocialClient.getPaintsByIds(paintIds).getPaintsList();
        return paintEntityConverter.convertToPaintResponse(gPaints);
    }

    public List<UserResponse> searchAllUsers(final SearchRequest searchRequest) {
        return searchUserService.searchAllUsersKeyword(searchRequest);
    }

    public PaintResponse searchRecentPaints() {
        return null;
    }
}
