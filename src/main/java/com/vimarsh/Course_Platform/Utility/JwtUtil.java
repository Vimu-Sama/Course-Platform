package com.vimarsh.Course_Platform.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(String email) {
        log.info("[JWT-UTIL] [METHOD: generateToken] [STEP: START] userEmail={}", email);

        try {
            String token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

            log.info("[JWT-UTIL] [METHOD: generateToken] [STEP: SUCCESS] userEmail={} | expires_in={}ms",
                    email, jwtExpiration);

            return token;

        } catch (Exception e) {
            log.error("[JWT-UTIL] [METHOD: generateToken] [STEP: ERROR] userEmail={} | error={}",
                    email, e.getMessage());
            throw e;
        }
    }
}