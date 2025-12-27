package com.deliorder.api.service.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokens {
    private String accessToken;
    private String refreshToken;
}
