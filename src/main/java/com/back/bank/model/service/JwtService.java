package com.back.bank.model.service;

import com.back.bank.model.dto.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    public String createToken(String userEmail, Token.Type token) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        Date now = new Date();
        return Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration * (token == Token.Type.A ? 1L : 30L)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean isExpired(String token) {
        try {
            final Date expiration = getClaimsFromToken(token).getExpiration();
            return !expiration.before(new Date());
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    public boolean isValid(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
//                .setSigningKey(new SecretKeySpec(key, SignatureAlgorithm.HS256.getJcaName()))
    }

    /*public String getTypeFromToken(String token) {
        return getClaimsFromToken(token).get("type", String.class);
    }

    public Long getUserSeqFromToken(String token) {
        return getClaimsFromToken(token).get("userSeq", Long.class);
    }*/



}
