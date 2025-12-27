package com.deliorder.api.common.dto;

import com.deliorder.api.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nickname;

    public AuthUser(Long id, String email, UserRole userRole, String nickname) {
        this.id = id;
        this.email = email;
        this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
        this.nickname = nickname;
    }

}
