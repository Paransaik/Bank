package com.back.bank.model.service;

import com.back.bank.model.dto.Token;
import com.back.bank.model.dto.TokenDTO;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private Long expiration;

    private byte[] key;

    @PostConstruct
    public void init() {
        this.key = secret.getBytes(StandardCharsets.UTF_8);
    }

    public String createToken(String userEmail, Token token) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        Date now = new Date();
        logger.debug(String.valueOf(expiration));
        return Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration * (token == Token.A ? 1L : 30L)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean isExpired(String token) {
        try {
            final Date expiration = getClaimsFromToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    /*public String getTypeFromToken(String token) {
        return getClaimsFromToken(token).get("type", String.class);
    }


    public Long getUserSeqFromToken(String token) {
        return getClaimsFromToken(token).get("userSeq", Long.class);
    }*/

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(new SecretKeySpec(key, SignatureAlgorithm.HS256.getJcaName()))
                .parseClaimsJws(token)
                .getBody();
    }

    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
    public boolean isUsable(String jwt) {
        try {
            Jws<Claims> token = Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt);
            logger.info("token {}", token);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
