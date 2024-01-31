package org.palette.passport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.passport.component.Passport;
import org.palette.passport.component.UserInfo;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class PassportExtractor {

    private static final String USER_INFO = "userInfo";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final ObjectMapper objectMapper;
    private final PassportValidator passportValidator;

    public Passport getPassportFromRequestHeader(HttpServletRequest httpServletRequest) {
        try {
            return objectMapper.readValue(
                    new String(
                            Base64.getDecoder().decode(httpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME)),
                            StandardCharsets.UTF_8
                    ),
                    Passport.class
            );
        } catch (JsonProcessingException e) {
            throw new BaseException(ExceptionType.COMMON_500_000002);
        }
    }

    public UserInfo getUserInfoByPassport(Passport passport) {
        try {
            String passportString = new String(
                    Base64.getDecoder().decode(passport.toString())
            );

            passportValidator.validatePassport(passportString);

            String userInfoString = objectMapper.readTree(passportString)
                    .get(USER_INFO)
                    .toString();
            return objectMapper.readValue(
                    userInfoString,
                    UserInfo.class
            );
        } catch (JsonProcessingException e) {
            throw new BaseException(ExceptionType.COMMON_500_000002);
        }
    }
}
