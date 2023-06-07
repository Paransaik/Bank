package com.back.bank.interceptor;

import com.back.bank.model.dto.TokenDTO;
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
    private long accessTokenValidTime = 60 * 60 * 1000;
    private long refreshTokenValidTime = 30 * 60 * 60 * 1000;

    public TokenDTO createAccessAndRefresh(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        Date now = new Date();

        String accessToken = Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes(StandardCharsets.UTF_8))
                .compact();

        String refreshToken = Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes(StandardCharsets.UTF_8))
                .compact();

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .key(userEmail)
                .build();
    }

    public String recreateAccessToken(String userEmail){
        Claims claims = Jwts.claims().setSubject(userEmail);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
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
