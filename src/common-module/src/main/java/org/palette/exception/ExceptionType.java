package org.palette.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    // 400
    COMMON_400_000001(
            "COMMON_400_000001",
            "INCORRECT_PARAMETER",
            "요청 파라미터가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    COMMON_400_000002(
            "COMMON_400_000002",
            "BROKEN_TOKEN",
            "요청 토큰이 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    COMMON_400_000003(
            "COMMON_400_000003",
            "BROKEN_ATTEMPT",
            "인증번호 입력 횟수를 초과했습니다.",
            HttpStatus.BAD_REQUEST
    ),

    COMMON_400_000004(
            "COMMON_400_000002",
            "BROKEN_PAYLOAD",
            "인증번호가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 401
    COMMON_401_000001(
            "COMMON_401_000001",
            "MISSED COMMONENTICATION",
            "해당 요청은 인증이 필요합니다.",
            HttpStatus.UNAUTHORIZED
    ),

    // 403
    COMMON_403_000001(
            "COMMON_403_000001",
            "NOT ALLOWED PERMISSION",
            "해당 요청에 대한 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),

    COMMON_403_000002(
            "COMMON_403_000002",
            "EXPIRED_PAYLOAD",
            "요청한 인증번호가 만료되었습니다.",
            HttpStatus.FORBIDDEN
    ),

    COMMON_403_000003(
            "COMMON_403_000003",
            "EXPIRED_PAYLOAD",
            "토큰이 만료되었습니다. 다시 로그인해주세요",
            HttpStatus.FORBIDDEN
    ),

    // 404
    COMMON_404_000001(
            "COMMON_404_000001",
            "NOT FOUNDED",
            "해당 리소스가 존재하지 않습니다.",
            HttpStatus.NOT_FOUND
    ),

    // 405
    COMMON_405_000001(
            "COMMON_403_000005",
            "NOT ALLOWED METHOD",
            "올바르지 않은 요청 메서드입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    // 409
    COMMON_409_000001(
            "COMMON_409_000001",
            "DUPLICATED",
            "중복된 리소스가 있습니다.",
            HttpStatus.CONFLICT
    ),

    // 500
    COMMON_500_000001(
            "COMMON_500_000001",
            "INTERNAL SERVER ERROR",
            "서버 간 통신 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    COMMON_500_000002(
            "COMMON_500_000002",
            "INTERNAL SERVER ERROR",
            "Passport를 처리하는 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    COMMON_500_000003(
            "COMMON_500_000003",
            "INTERNAL SERVER ERROR",
            "Passport를 등록하는 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    ;

    private final String code;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
