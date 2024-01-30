package org.palette.easelsocialservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.request.LikePaintRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUsecase {
    public void likePaint(Long userId, LikePaintRequest likePostRequest) {
    }
}
