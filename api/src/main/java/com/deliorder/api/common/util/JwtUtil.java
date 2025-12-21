package com.deliorder.api.common.util;

import com.deliorder.api.common.config.JwtProperties;
import com.deliorder.api.common.constants.SecurityConstants;
import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import com.deliorder.api.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(jwtProperties.getSecret());
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long userId, String email, UserRole userRole, String nickname) {
        Date date = new Date();

        return SecurityConstants.BEARER_PREFIX +
                Jwts.builder()
                        .subject(String.valueOf(userId))
                        .claim("email", email)
                        .claim("userRole", userRole)
                        .claim("nickname", nickname)
                        .issuedAt(date)
                        .expiration(new Date(date.getTime() + SecurityConstants.REFRESH_TOKEN_TTL))
                        .id(UUID.randomUUID().toString())
                        .signWith(key, Jwts.SIG.HS256)
                        .compact();
    }

    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(SecurityConstants.BEARER_PREFIX)) {
            return token.substring(7);
        }

        throw new HandledException(ErrorCode.NOT_FOUND, "토큰이 존재하지 않습니다.");
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
