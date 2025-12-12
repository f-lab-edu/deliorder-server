package com.deliorder.api.api.dto;

import com.deliorder.api.entity.User;
import com.deliorder.api.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
    private Long id;
    private String email;
    private String name;
    private final String nickname;
    private String phoneNumber;
    private UserRole userRole;

    public static SignupResponse of(User user) {
        return SignupResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .userRole(user.getUserRole())
                .build();
    }
}
