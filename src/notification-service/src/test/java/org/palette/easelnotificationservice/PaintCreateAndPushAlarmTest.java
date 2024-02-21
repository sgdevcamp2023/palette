package org.palette.easelnotificationservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palette.easelnotificationservice.external.socket.PushAlarmSocketHandler;
import org.palette.easelnotificationservice.persistence.User;
import org.palette.easelnotificationservice.persistence.UserJpaRepository;
import org.palette.easelnotificationservice.persistence.enums.AlarmAllowedType;
import org.palette.easelnotificationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.palette.easelnotificationservice.external.socket.PushAlarmMessageConst.PAINT_CREATED_BY_FOLLOWING_USER;

@SpringBootTest
class PaintCreateAndPushAlarmTest {

    @Autowired
    private UserService userService;

    private UserJpaRepository userJpaRepository;

    @Autowired
    private PushAlarmSocketHandler pushAlarmSocketHandler;

    @BeforeEach
    void insertUser() {
        List<User> users = Stream.of("글쓴이", "팔로워2", "팔로워3", "팔로워4", "팔로워5", "팔로워6")
                .map(nickname -> User.build(nickname, ""))
                .collect(Collectors.toList());
        userJpaRepository.saveAll(users);
    }

    @Test
    void test() {
        final List<Long> followerIds = new ArrayList<>() {{
            add(2L);
            add(3L);
            add(4L);
            add(5L);
            add(6L);
        }};

        final List<User> followers = userService.loadAllByIds(followerIds);
        final User writer = userService.loadById(1L);
        pushAlarmSocketHandler.broadcast(
                new TextMessage(PAINT_CREATED_BY_FOLLOWING_USER.getValue(writer.getNickname())),
                AlarmAllowedType.PAINT_CREATED,
                1L
        );
    }
}
