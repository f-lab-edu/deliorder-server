package com.deliorder.api.enums;

import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_USER(Authority.USER),
    ROLE_OWNER(Authority.OWNER),
    ROLE_ADMIN(Authority.ADMIN);

    private final String userRole;

    public static UserRole of(String userRole) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(userRole))
                .findFirst()
                .orElseThrow(() -> new HandledException(ErrorCode.INVALID_USER_ROLE));
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String OWNER = "ROLE_OWNER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
