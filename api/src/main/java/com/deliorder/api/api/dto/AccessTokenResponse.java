package com.deliorder.api.api.dto;

import com.deliorder.api.service.result.AuthTokens;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class AccessTokenResponse {

    private final String accessToken;

    public static AccessTokenResponse fromAuthTokens(AuthTokens tokens) {
        return AccessTokenResponse.builder()
                .accessToken(tokens.getAccessToken())
                .build();
    }
}
