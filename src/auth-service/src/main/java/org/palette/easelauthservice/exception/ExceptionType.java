package org.palette.easelauthservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // 400
    AUTH_400_000001(
            "AUTH_400_000001",
            "INCORRECT_PARAMETER",
            "요청 파라미터가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    AUTH_400_000002(
            "AUTH_400_000002",
            "BROKEN_TOKEN",
            "요청 토큰이 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    AUTH_400_000003(
            "AUTH_400_000002",
            "BROKEN_ATTEMPT",
            "인증번호 입력 횟수를 초과했습니다.",
            HttpStatus.BAD_REQUEST
    ),

    AUTH_400_000004(
            "AUTH_400_000002",
            "BROKEN_PAYLOAD",
            "인증번호가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 401
    AUTH_401_000001(
            "AUTH_401_000001",
            "MISSED AUTHENTICATION",
            "해당 요청은 인증이 필요합니다.",
            HttpStatus.UNAUTHORIZED
    ),

    // 403
    AUTH_403_000001(
            "AUTH_403_000001",
            "NOT ALLOWED PERMISSION",
            "해당 요청에 대한 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),

    AUTH_403_000002(
            "AUTH_403_000002",
            "EXPIRED_PAYLOAD",
            "요청한 인증번호가 만료되었습니다.",
            HttpStatus.FORBIDDEN
    ),

    // 404
    AUTH_404_000001(
            "AUTH_404_000001",
            "NOT FOUNDED",
            "해당 리소스가 존재하지 않습니다.",
            HttpStatus.NOT_FOUND
    ),

    // 405
    AUTH_405_000001(
            "AUTH_403_000005",
            "NOT ALLOWED METHOD",
            "올바르지 않은 요청 메서드입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    // 409
    AUTH_409_000001(
            "AUTH_409_000001",
            "DUPLICATED",
            "중복된 리소스가 있습니다.",
            HttpStatus.CONFLICT
    ),

    // 409
    AUTH_500_000001(
            "AUTH_500_000001",
            "INTERNAL SERVER ERROR",
            "서버 간 통신 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    ;

    private final String code;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
