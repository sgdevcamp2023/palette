package org.palette.easelsearchservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.dto.event.UserCreatedEvent;
import org.palette.easelsearchservice.dto.request.SearchRequest;
import org.palette.easelsearchservice.dto.response.UserResponse;
import org.palette.easelsearchservice.persistence.SearchUser;
import org.palette.easelsearchservice.persistence.SearchUserRepository;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchUserService {
    private final SearchUserRepository searchUserRepository;

    public void createUser(final UserCreatedEvent userCreatedEvent) {
        searchUserRepository.save(
                SearchUser.build(
                        userCreatedEvent.id(),
                        userCreatedEvent.nickname(),
                        userCreatedEvent.username(),
                        userCreatedEvent.introduce(),
                        userCreatedEvent.profileImagePath()
                )
        );
    }

    public List<UserResponse> searchAllUsersKeyword(final SearchRequest searchRequest) {
        final Page<SearchUser> users = searchUserRepository.findByIntroduceContainingOrNicknameContaining(
                searchRequest.keyword(),
                PageRequest.of(searchRequest.page(), searchRequest.size())
        );
        return convertToUserResponse(users);
    }

    public void updateUser(final UpdateUserEvent updateUserEvent) {
        final SearchUser user = searchUserRepository.findById(updateUserEvent.userId())
                .orElseThrow(() -> new BaseException(ExceptionType.USER_404_000001));

        searchUserRepository.deleteById(updateUserEvent.userId());

        searchUserRepository.save(
                SearchUser.build(
                        user.getId(),
                        updateUserEvent.nickname(),
                        user.getUsername(),
                        updateUserEvent.introduce(),
                        updateUserEvent.profileImagePath()
                )
        );
    }

    private List<UserResponse> convertToUserResponse(Page<SearchUser> searchUsers) {
        return searchUsers.stream()
                .map(user -> new UserResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getNickname(),
                                user.getProfileImagePath(),
                                "true",
                                false
                        )
                )
                .toList();
    }
}
