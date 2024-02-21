package org.palette.easelsearchservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.dto.event.UserCreatedEvent;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.PaintResponse;
import org.palette.easelsearchservice.dto.response.UserResponse;
import org.palette.easelsearchservice.service.SearchPaintService;
import org.palette.easelsearchservice.service.SearchUserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchUsecase {

    private final SearchPaintService searchPaintService;
    private final SearchUserService searchUserService;

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
        return searchPaintService.searchAllPaints(searchRequest);
    }

    public List<UserResponse> searchAllUsers(final SearchRequest searchRequest) {
        return searchUserService.searchAllUsersKeyword(searchRequest);
    }

    public PaintResponse searchRecentPaints() {
        return null;
    }
}
