package com.deliorder.api.api;

import com.deliorder.api.api.dto.SigninRequest;
import com.deliorder.api.api.dto.AccessTokenResponse;
import com.deliorder.api.api.dto.SignupRequest;
import com.deliorder.api.api.dto.SignupResponse;
import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.common.dto.AuthUser;
import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import com.deliorder.api.common.util.CookieUtil;
import com.deliorder.api.entity.User;
import com.deliorder.api.service.AuthService;
import com.deliorder.api.service.result.AuthTokens;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        User newUser = authService.signup(SignupRequest.toEntity(request));
        SignupResponse response = SignupResponse.of(newUser);
        return ResponseEntity.ok(ApiResponse.success("", response));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> signin(
            @Valid @RequestBody SigninRequest request,
            HttpServletResponse response
    ) {
        AuthTokens authTokens = authService.signin(SigninRequest.toCommand(request));
        CookieUtil.addRefreshTokenToCookie(response, authTokens.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("", AccessTokenResponse.fromAuthTokens(authTokens)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> refreshToken(
            @CookieValue(name = CookieUtil.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken,
            @AuthenticationPrincipal AuthUser authUser,
            HttpServletResponse response
    ) {
        checkRefreshTokenExists(refreshToken);

        AuthTokens authTokens = authService.refreshToken(refreshToken, authUser.getId());
        CookieUtil.addRefreshTokenToCookie(response, authTokens.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("", AccessTokenResponse.fromAuthTokens(authTokens)));
    }

    private void checkRefreshTokenExists(String refreshToken) {
        if (refreshToken == null) {
            throw new HandledException(ErrorCode.MISSING_REFRESH_TOKEN);
        }
    }
}
