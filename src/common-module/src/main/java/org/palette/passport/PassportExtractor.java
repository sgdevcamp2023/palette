package org.palette.passport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.passport.component.UserInfo;

import java.util.Base64;

@RequiredArgsConstructor
public class PassportExtractor {

    private static final String USER_INFO = "userInfo";
    private final ObjectMapper objectMapper;

    public UserInfo getUserInfo(String requestedPassport) {
        UserInfo userInfo;
        try {
            String passportStr = new String(
                    Base64.getDecoder().decode(requestedPassport)
            );
            String userInfoString = objectMapper.readTree(passportStr)
                    .get(USER_INFO)
                    .toString();
            userInfo = objectMapper.readValue(
                    userInfoString,
                    UserInfo.class
            );
        } catch (JsonProcessingException e) {
            throw new BaseException(ExceptionType.COMMON_500_000002);
        }
        return userInfo;
    }
}
