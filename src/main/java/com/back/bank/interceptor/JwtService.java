package com.back.bank.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String SALT = "dimple";
    private static final int EXPIRE_MINUTES = 60;

    public <T> String create(String key, T data, String subject) {
        return Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
                .setSubject(subject)
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
    public boolean isUsable(String jwt) {
        try {
            Jws<Claims> token = Jwts
                    .parser()
                    .setSigningKey(SALT.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt);
            logger.debug("token {}", token);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
