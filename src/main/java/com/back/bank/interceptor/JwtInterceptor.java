package com.back.bank.interceptor;

import com.back.bank.model.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    private static final String HEADER_AUTH = "token";

    private final JwtService jwtService;

    @Autowired
    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);
        /*if (token == null || !jwtService.isExpired(token)) {
            logger.info("토큰 사용 불가능 : {}", token);
            return false;
        }*/

        if (token == null || !jwtService.isUsable(token)) {
            logger.info("토큰 사용 불가능 : {}", token);
            return false;
        }
        logger.info("토큰 사용 가능 : {}", token);
        return true;
    }
}
