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

}
