package com.back.bank.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * UnsupportedJwtException : jwt가 예상하는 형식과 다른 형식이거나 구성
 * MalformedJwtException : 잘못된 jwt 구조
 * ExpiredJwtException : JWT의 유효기간이 초과
 * SignatureException : JWT의 서명실패(변조 데이터)
 * 
 * TODO: e.printStackTrace()는 원치 않은 에러까지 프론트에 보여줄 수 있기 때문에 Logger로 바꿀 것
 * */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response ServerException2(Exception e) {
        e.printStackTrace();
        return new Response("500", "서버 에러");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response MissingRequestHeaderException(Exception e) {
        e.printStackTrace();
        return new Response("400", "MissingRequestHeaderException");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response UnsupportedJwtException(Exception e) {
        e.printStackTrace();
        return new Response("401", "UnsupportedJwtException");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response MalformedJwtException(Exception e) {
        e.printStackTrace();
        return new Response("402", "MalformedJwtException");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response ExpiredJwtException(Exception e) {
        e.printStackTrace();
        return new Response("403", "ExpiredJwtException");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response SignatureException(Exception e) {
        e.printStackTrace();
        return new Response("404", "SignatureException");
    }

    //Response DTO
    @Data
    @AllArgsConstructor
    static class Response {
        private String code;
        private String msg;
    }
}