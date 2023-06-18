package com.back.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionController {

    // TODO: e.printStackTrace()는 원치 않은 에러까지 프론트에 보여줄 수 있기 때문에 Logger로 바꿀 것
    @ExceptionHandler({ErrorCode.ErrorException.class})
    public ResponseEntity<ErrorCode.Dto> exceptionHandler(final ErrorCode.ErrorException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ErrorCode.Dto.builder()
                        .errorMessage(e.getError().getMessage())
                        .build());
    }
}