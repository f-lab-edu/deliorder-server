package com.deliorder.api.service;

import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import com.deliorder.api.entity.User;
import com.deliorder.api.repository.UserRepository;
import com.deliorder.api.service.command.SigninCommand;
import com.deliorder.api.service.result.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @Transactional
    public User signup(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new HandledException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.updatePassword(encodedPassword);

        return userRepository.save(newUser);
    }

    public AuthTokens signin(SigninCommand command) {
        User user = userService.getUserByEmail(command.getEmail());

        if (user.getDeletedAt() != null) {
            throw new HandledException(ErrorCode.AUTHORIZATION, "이미 탈퇴한 사용자입니다.");
        }

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new HandledException(ErrorCode.AUTHORIZATION, "비밀번호가 일치하지 않습니다.");
        }

        return createAuthTokens(user);
    }

    public AuthTokens refreshToken(String refreshToken, Long userId) {
        Long validatedUserId = tokenService.validateRefreshToken(refreshToken, userId);
        tokenService.deleteRefreshToken(refreshToken);

        User user = userService.getUser(validatedUserId);

        return createAuthTokens(user);
    }

    private AuthTokens createAuthTokens(User user) {
        String accessToken = tokenService.createAccessToken(user);
        String refreshToken = tokenService.createRefreshToken(user);

        return AuthTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
