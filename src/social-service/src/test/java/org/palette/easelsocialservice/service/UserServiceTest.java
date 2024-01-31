package org.palette.easelsocialservice.service;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getFollowingCountTest() {
        // given
        Long userId = 1L;

        // when
        int followingCount = userRepository.countFollowings(userId);

        // then
        int expectedCount = 0;
        assertEquals(expectedCount, followingCount, "count 오류");
    }

}
