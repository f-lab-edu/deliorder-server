package com.deliorder.api.api;

import com.deliorder.api.api.dto.SigninRequest;
import com.deliorder.api.api.dto.AccessTokenResponse;
import com.deliorder.api.api.dto.SignupRequest;
import com.deliorder.api.api.dto.SignupResponse;
import com.deliorder.api.common.constants.SecurityConstants;
import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.entity.User;
import com.deliorder.api.service.AuthService;
import com.deliorder.api.service.result.AuthTokens;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        addRefreshTokenToCookie(response, authTokens.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("", AccessTokenResponse.fromAuthTokens(authTokens)));
    }

    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) SecurityConstants.REFRESH_TOKEN_TTL);
        response.addCookie(cookie);
    }
}
