package org.palette.easelauthservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionResponse;
import org.palette.exception.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
    })
    public ResponseEntity<ExceptionResponse> handleRequestValidationException(Exception e) {
        ExceptionType exceptionType = ExceptionType.AUTH_400_000001;
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(exceptionType.getCode())
                .message(exceptionType.getMessage())
                .description(exceptionType.getDescription())
                .build();

        logException(exceptionResponse);

        return ResponseEntity
                .status(exceptionType.getHttpStatus())
                .body(exceptionResponse);
    }

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ExceptionResponse> handleBaseException(
            BaseException baseException
    ) {
        ExceptionType exceptionType = baseException.getExceptionType();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(exceptionType.getCode())
                .description(exceptionType.getDescription())
                .message(exceptionType.getMessage())
                .build();

        logException(exceptionResponse);

        return ResponseEntity
                .status(exceptionType.getHttpStatus())
                .body(exceptionResponse);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code("AUTH_SERVICE_EXCEPTION")
                .message(e.getCause().getMessage())
                .description(e.getLocalizedMessage())
                .build();

        logInternalException(e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }


    private static void logException(ExceptionResponse exceptionResponse) {
        log.error(
                "code : {}, message : {}, description : {}",
                exceptionResponse.code(),
                exceptionResponse.description(),
                exceptionResponse.message()
        );
    }

    private static void logInternalException(Exception e) {
        log.error(e.getLocalizedMessage());
    }
}
