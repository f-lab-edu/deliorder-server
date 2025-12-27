package com.deliorder.api.service;

import com.deliorder.api.common.constants.RedisConstants;
import com.deliorder.api.common.constants.SecurityConstants;
import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import com.deliorder.api.common.util.JwtUtil;
import com.deliorder.api.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    public String createAccessToken(User user) {
        return jwtUtil.createToken(
                user.getId(),
                user.getEmail(),
                user.getUserRole(),
                user.getNickname()
        );
    }

    public String createRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();
        String redisKey = RedisConstants.REFRESH_TOKEN_KEY_PREFIX + refreshToken;

        try {
            redisTemplate.opsForValue().set(
                    redisKey,
                    String.valueOf(user.getId()),
                    SecurityConstants.REFRESH_TOKEN_TTL,
                    TimeUnit.SECONDS
            );
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            log.warn("[Redis] RefreshToken Redis 저장 실패: {}", e.getMessage());
            throw new HandledException(ErrorCode.REDIS_CONNECTION_ERROR);
        }

        return refreshToken;
    }

    public Long validateRefreshToken(String refreshToken, Long userId) {
        String redisKey = RedisConstants.REFRESH_TOKEN_KEY_PREFIX + refreshToken;

        try {
            String userIdValue = redisTemplate.opsForValue().get(redisKey);

            if (userIdValue == null) {
                throw new HandledException(ErrorCode.EXPIRED_REFRESH_TOKEN);
            }

            if (!userIdValue.equals(String.valueOf(userId))) {
                throw new HandledException(ErrorCode.INVALID_REFRESH_TOKEN_OWNER);
            }

            return Long.valueOf(userIdValue);
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            log.error("[Redis] RefreshToken 검증 중 Redis 오류 발생", e);
            throw new HandledException(ErrorCode.REDIS_CONNECTION_ERROR);
        }
    }

    public void deleteRefreshToken(String refreshToken) {
        String redisKey = RedisConstants.REFRESH_TOKEN_KEY_PREFIX + refreshToken;

        try {
            Boolean deleted = redisTemplate.delete(redisKey);

            if (!deleted) {
                log.warn("[Redis] RefreshToken 삭제 실패. 토큰값: {}", refreshToken);
            }
        } catch (RedisConnectionFailureException | RedisSystemException e) {
            log.warn("[Redis] RefreshToken 삭제 중 Redis 오류 발생: {}", e.getMessage());
        }
    }
}
