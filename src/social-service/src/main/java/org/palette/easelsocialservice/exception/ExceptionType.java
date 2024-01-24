package org.palette.easelsocialservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    SOCIAL_400_000001(
            "SOCIAL_400_000001",
            "NOT_FOUND_USER",
            "존재하지 않는 사용자입니다",
            HttpStatus.BAD_REQUEST
    ),

    SOCIAL_400_000002(
            "SOCIAL_400_000002",
            "NOT_FOUND_PAINT",
            "존재하지 않는 Paint입니다",
            HttpStatus.BAD_REQUEST
    ),

    SOCIAL_400_000003(
            "SOCIAL_400_000003",
            "DUPLICATE_USER",
            "이미 존재하는 사용자입니다.",
            HttpStatus.BAD_REQUEST
    );

    private final String code;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
