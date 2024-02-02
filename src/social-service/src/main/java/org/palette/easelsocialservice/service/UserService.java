package org.palette.easelsocialservice.service;


import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.response.UserResponse;
import org.palette.easelsocialservice.exception.BaseException;
import org.palette.easelsocialservice.exception.ExceptionType;
import org.palette.easelsocialservice.persistence.UserRepository;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user) {
        if (userRepository.existsByUid(user.getUid())) {
            throw new BaseException(ExceptionType.SOCIAL_400_000003);
        }

        userRepository.save(user);
    }

    public User getUser(final Long userId) {
        return userRepository.findByUid(userId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000001));
    }

    public void checkUserExists(final List<Long> mentionIds) {
        if (!userRepository.existsByAllUidsIn(mentionIds)) {
            throw new BaseException(ExceptionType.SOCIAL_400_000001);
        }
    }

    public Map<Long, User> getUserMapByUids(final List<Long> uids) {
        List<User> users = userRepository.findAllByUids(uids);
        Map<Long, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getUid(), user);
        }
        return userMap;
    }

    public List<User> getUsersByUids(final List<Long> uids) {
        return userRepository.findAllByUids(uids);
    }

    public void likePaint(Long userId, Paint paint) {
        if (userRepository.existsLikesByUidAndPid(userId, paint.getPid())) {
            throw new BaseException(ExceptionType.SOCIAL_400_000005);
        }
        User user = getUser(userId);
        user.likePaint(paint);
        userRepository.save(user);
    }

    public void unlike(final Long userId, final Long paintId) {
        userRepository.deleteLikeById(userId, paintId);
    }

    public void follow(Long userId, Long targetId) {
        User user = getUser(userId);
        User targetUser = getUser(targetId);

        user.addFollowing(targetUser);

        userRepository.save(user);
    }

    public void markPaint(Long userId, Paint paint) {
        if (userRepository.existsLikesByUidAndPid(userId, paint.getPid())) {
            throw new BaseException(ExceptionType.SOCIAL_400_000006);
        }
        User user = getUser(userId);
        user.marksPaint(paint);
        userRepository.save(user);
    }

    public void deleteMarkPaint(Long userId, Long paintId) {
        userRepository.deleteMarkById(userId, paintId);
    }

    public int getFollowingCount(Long userId) {
        return userRepository.countFollowings(userId);
    }

    public int getFollowerCount(Long userId) {
        return userRepository.countFollowers(userId);
    }

    public List<UserResponse> getLikedUsers(final Long paintId) {
        return convertToUserResponse(userRepository.findLikedByPaintId(paintId));
    }

    private List<UserResponse> convertToUserResponse(final List<User> users) {
        return users
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}
