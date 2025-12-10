package com.deliorder.api.entity;

import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_OWNER("ROLE_OWNER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String userRole;

    public static UserRole of(String userRole) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(userRole))
                .findFirst()
                .orElseThrow(() -> new HandledException(ErrorCode.INVALID_USER_ROLE));
    }
}
