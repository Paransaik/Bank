package com.back.bank.Auth;

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

    /**
     * AT |  RT  | 구현  |
     * O  |  O      O     : 서비스 정상 동작, 유효기간 끝나가면 AT 다시 갱신
     * O  |  X      O     : 
     * X  |  O      △     : Silence Refresh => Clinet가 getToken 호출
     * X  |  X      △     : 변조 혹은 만료된 Refresh Token으로 다시 로그인
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);
        if (token == null) {
            logger.info("Access Token이 Null이다. : {}", token);
            return false;
        }
        // TODO 클라이언트에서 AT, RT 만료를 검사한다고 가정
        if (!jwtService.isValid(token)) {
            logger.info("Access Token이 조작 됐다. : {}", token);
            // Refresh Token 폐기, 다시 로그인
            return false;
        }
        logger.info("Access Token 토큰 사용 가능 : {}", token);
        return true;
    }
}
