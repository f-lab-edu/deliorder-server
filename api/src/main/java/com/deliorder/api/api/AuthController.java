package com.deliorder.api.api;

import com.deliorder.api.api.dto.SignupRequest;
import com.deliorder.api.api.dto.SignupResponse;
import com.deliorder.api.common.dto.ApiResponse;
import com.deliorder.api.entity.User;
import com.deliorder.api.service.AuthService;
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
}
