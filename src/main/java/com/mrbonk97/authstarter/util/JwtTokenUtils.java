package com.mrbonk97.authstarter.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Slf4j
@Component
public class JwtTokenUtils {
    @Value("${jwt.secret-key}")
    private String secret;
    private static SecretKey secretKey;
    private static final Long accessTokenExpireDate = 3_600_000L; // 1 Hour
    private static final Long refreshTokenExpireDate = 2_629_746_000L; // 30 Days


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String email, String type) {
        long expireTime = type.equals("ACCESS") ? accessTokenExpireDate : refreshTokenExpireDate;

        return Jwts
                .builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .subject(email)
                .signWith(secretKey).compact();
    }

    public static String getEmail(String token) {
        Jws<Claims> jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        Claims claims = jws.getPayload();
        return claims.getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (SignatureException e) {
            log.info("Jwt 토큰 해독 실패: " + e.getMessage());
            return false;
        }
        catch (ExpiredJwtException e) {
            log.info("Jwt 토큰 만료: " + e.getMessage());
            return false;
        }

        return true;
    }
}
