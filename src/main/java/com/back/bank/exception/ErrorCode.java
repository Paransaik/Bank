package com.back.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ErrorCode {
    @Builder
    @AllArgsConstructor
    static class Dto {
        private HttpStatus status;
        private String errorMessage;
    }

    @Getter
    enum Type {
        /***
         * UnsupportedJwtException : jwt가 예상하는 형식과 다른 형식이거나 구성
         * MalformedJwtException : 잘못된 jwt 구조
         * ExpiredJwtException : JWT의 유효기간이 초과
         * SignatureException : JWT의 서명실패(변조 데이터)
         * */

        /*
         * 400 BAD_REQUEST: 잘못된 요청
         */
        EXPIREDTOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),

        /*
         * 401
         */
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

        /*
         * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
         */
        COUNTERFEIT(HttpStatus.INTERNAL_SERVER_ERROR, "위조시도");

        private final HttpStatus status;
        private final String message;

        Type(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    @Getter
    static class ErrorException extends Exception {
        private final ErrorCode.Type error;

        public ErrorException(ErrorCode.Type e) {
            super(e.getMessage());
            this.error = e;
        }
    }
}