package org.palette.easelnotificationservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easelnotificationservice.exception.BaseException;
import org.palette.easelnotificationservice.exception.ExceptionType;
import org.palette.easelnotificationservice.persistence.User;
import org.palette.easelnotificationservice.persistence.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public List<User> loadAllByIds(List<Long> ids) {
        return userJpaRepository.findAllById(ids);
    }

    public User loadById(Long id) {
        return userJpaRepository.findById(id).orElseThrow(() ->
                new BaseException(ExceptionType.NOTIFICATION_404_000001)
        );
    }
}
