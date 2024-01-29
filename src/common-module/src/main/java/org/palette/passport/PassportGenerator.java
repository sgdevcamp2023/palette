package org.palette.passport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.passport.component.Passport;
import org.palette.passport.component.UserInfo;

import java.util.Base64;

@RequiredArgsConstructor
public class PassportGenerator {

    private final ObjectMapper objectMapper;
    private final HMACEncoder hmacEncoder;

    public String generatePassport(UserInfo userInfo) {
        String encodedPassportString;

        try {
            String userInfoString = objectMapper.writeValueAsString(userInfo);
            String integrityKey = hmacEncoder.createHMACIntegrityKey(userInfoString);

            Passport passport = new Passport(userInfo, integrityKey);
            String passportString = objectMapper.writeValueAsString(passport);
            encodedPassportString = Base64.getEncoder()
                    .encodeToString(passportString.getBytes());
        } catch (JsonProcessingException e) {
            throw new BaseException(ExceptionType.COMMON_500_000002);
        }

        return encodedPassportString;
    }

}
