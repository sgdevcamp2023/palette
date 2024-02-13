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

    // 400
    GATEWAY_400_000001(
            "GATEWAY_400_000001",
            "INCORRECT_PARAMETER",
            "요청 파라미터가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 401
    GATEWAY_401_000001(
            "GATEWAY_401_000001",
            "MISSED AUTHENTICATION",
            "해당 요청은 인증이 필요합니다.",
            HttpStatus.UNAUTHORIZED
    ),

    // 403
    GATEWAY_403_000001(
            "GATEWAY_403_000001",
            "NOT ALLOWED PERMISSION",
            "해당 요청에 대한 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),


    // 404
    GATEWAY_404_000001(
            "GATEWAY_404_000001",
            "NOT FOUNDED",
            "해당 리소스가 존재하지 않습니다.",
            HttpStatus.NOT_FOUND
    ),

    // 405
    GATEWAY_405_000001(
            "GATEWAY_405_000001",
            "NOT ALLOWED METHOD",
            "올바르지 않은 요청 메서드입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    // 409
    GATEWAY_409_000001(
            "GATEWAY_409_000001",
            "DUPLICATED",
            "중복된 리소스가 있습니다.",
            HttpStatus.CONFLICT
    ),

    GATEWAY_500_000001(
            "GATEWAY_500_000001",
            "INTERNAL_SERVER_ERROR",
            "서버 내부에 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    // 400
    USER_400_000001(
            "USER_400_000001",
            "INCORRECT_PARAMETER",
            "요청 파라미터가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    USER_400_000002(
            "USER_400_000002",
            "INCORRECT_EMAIL_PASSWORD",
            "이메일 혹은 비밀번호가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 401
    USER_401_000001(
            "USER_401_000001",
            "MISSED AUTHENTICATION",
            "해당 요청은 인증이 필요합니다.",
            HttpStatus.UNAUTHORIZED
    ),

    // 403
    USER_403_000001(
            "USER_403_000001",
            "NOT ALLOWED PERMISSION",
            "해당 요청에 대한 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),

    // 404
    USER_404_000001(
            "USER_404_000001",
            "NOT FOUNDED",
            "해당 리소스가 존재하지 않습니다.",
            HttpStatus.NOT_FOUND
    ),

    // 405
    USER_405_000001(
            "USER_403_000005",
            "NOT ALLOWED METHOD",
            "올바르지 않은 요청 메서드입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    // 409
    USER_409_000001(
            "USER_409_000001",
            "DUPLICATED",
            "중복된 리소스가 있습니다.",
            HttpStatus.CONFLICT
    ),

    // 500
    USER_500_000001(
            "AUTH_500_000001",
            "INTERNAL SERVER ERROR",
            "서버 간 통신 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

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
            "AUTH_400_000003",
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

    AUTH_403_000003(
            "AUTH_403_000003",
            "EXPIRED_PAYLOAD",
            "토큰이 만료되었습니다. 다시 로그인해주세요",
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

    // 500
    AUTH_500_000001(
            "AUTH_500_000001",
            "INTERNAL SERVER ERROR",
            "서버 간 통신 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    // 400
    NOTIFICATION_400_000001(
            "NOTIFICATION_400_000001",
            "INCORRECT_PARAMETER",
            "요청 파라미터가 올바르지 않습니다.",
            HttpStatus.BAD_REQUEST
    ),

    // 401
    NOTIFICATION_401_000001(
            "NOTIFICATION_401_000001",
            "MISSED AUTHENTICATION",
            "해당 요청은 인증이 필요합니다.",
            HttpStatus.UNAUTHORIZED
    ),

    // 403
    NOTIFICATION_403_000001(
            "NOTIFICATION_403_000001",
            "NOT ALLOWED PERMISSION",
            "해당 요청에 대한 권한이 없습니다.",
            HttpStatus.FORBIDDEN
    ),

    // 404
    NOTIFICATION_404_000001(
            "NOTIFICATION_404_000001",
            "NOT FOUNDED",
            "해당 리소스가 존재하지 않습니다.",
            HttpStatus.NOT_FOUND
    ),

    // 405
    NOTIFICATION_405_000001(
            "NOTIFICATION_403_000005",
            "NOT ALLOWED METHOD",
            "올바르지 않은 요청 메서드입니다.",
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    // 409
    NOTIFICATION_409_000001(
            "NOTIFICATION_409_000001",
            "DUPLICATED",
            "중복된 리소스가 있습니다.",
            HttpStatus.CONFLICT
    ),

    // 500
    NOTIFICATION_500_000001(
            "AUTH_500_000001",
            "INTERNAL SERVER ERROR",
            "서버 간 통신 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    NOTIFICATION_500_000002(
            "AUTH_500_000002",
            "INTERNAL SERVER ERROR",
            "푸쉬 알림 전송 중 예기치 못한 오류가 발생했습니다.",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

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
    ),

    SOCIAL_400_000004(
            "SOCIAL_400_000004",
            "DUPLICATE_REPAINT",
            "이미 재게시 있는 게시글 입니다",
            HttpStatus.BAD_REQUEST
    ),

    SOCIAL_400_000005(
            "SOCIAL_400_000005",
            "DUPLICATE_LIKE",
            "이미 좋아요 있는 게시글 입니다",
            HttpStatus.BAD_REQUEST
    ),

    SOCIAL_400_000006(
            "SOCIAL_400_000006",
            "DUPLICATE_MARK",
            "이미 보관 있는 게시글 입니다",
            HttpStatus.BAD_REQUEST
    );

    ;

    private final String code;
    private final String message;
    private final String description;
    private final HttpStatus httpStatus;
}
