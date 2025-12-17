package com.deliorder.api.api.dto;

import com.deliorder.api.service.command.SigninCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninRequest {

    @Email
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

    public static SigninCommand toCommand(SigninRequest request) {
        return SigninCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
