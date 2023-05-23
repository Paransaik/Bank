package com.back.miru.interceptor;

import com.back.miru.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

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
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
    }

    private byte[] generateKey() {
        return SALT.getBytes(StandardCharsets.UTF_8);
    }

    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
    public boolean isUsable(String jwt) {
        try {
            Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public Map<String, Object> get(String key) {
        // 1. Jwt 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("access-token");

        // 2. Jwt parsing
        Jws<Claims> claims;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(SALT.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UnauthorizedException();
        }

        // 3. Value 추출
        Map<String, Object> value = claims.getBody();
        logger.info("value : {}", value);
        return value;
    }
}
