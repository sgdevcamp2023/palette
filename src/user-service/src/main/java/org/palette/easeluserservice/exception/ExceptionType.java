package org.palette.easeluserservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // 400
    USER_000001(
            "USER_000001",
            "INCORRECT_PARAMETER",
            "요청 파라미터가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 401
    USER_000002(
            "USER_000002",
            "MISSED AUTHENTICATION",
            "해당 요청은 인증이 필요합니다.",
            HttpStatus.UNAUTHORIZED
    ),

    // 403
    USER_000003(
            "USER_000003",
            "NOT ALLOWED PERMISSION",
            "해당 요청에 대한 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),


    // 404
    USER_000004(
            "USER_000004",
            "NOT FOUNDED",
            "해당 리소스가 존재하지 않습니다.",
            HttpStatus.NOT_FOUND
    ),

    // 405
    USER_000005(
            "USER_000005",
            "NOT ALLOWED METHOD",
            "올바르지 않은 요청 메서드입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    // 409
    USER_000006(
            "USER_000006",
            "DUPLICATED",
            "중복된 리소스가 있습니다.",
            HttpStatus.CONFLICT
    ),

    USER_000007(
            "USER_000007",
            "BROKEN_TOKEN",
            "요청 토큰이 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    USER_000008(
            "USER_000008",
            "EXPIRED_TOKEN",
            "요청 토큰이 만료되었습니다.",
            HttpStatus.FORBIDDEN
    ),

    ;

    private final String code;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
