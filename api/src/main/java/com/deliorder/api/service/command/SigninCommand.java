package com.deliorder.api.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SigninCommand {
    private final String email;
    private final String password;
}
