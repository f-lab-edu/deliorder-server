package com.deliorder.api.common.filter;


import com.deliorder.api.common.constants.SecurityConstants;
import com.deliorder.api.common.dto.AuthUser;
import com.deliorder.api.common.security.token.JwtAuthenticationToken;
import com.deliorder.api.common.util.JwtUtil;
import com.deliorder.api.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            String jwt = jwtUtil.substringToken(authorizationHeader);

            try {
                Claims claims = jwtUtil.extractClaims(jwt);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    setAuthentication(claims);
                }
            } catch (SecurityException | MalformedJwtException e) {
                String message = "유효하지 않은 JWT 토큰입니다.";
                log.error(message, e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
            } catch (ExpiredJwtException e) {
                String message = "만료된 JWT 토큰입니다.";
                log.error(message, e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
            } catch (UnsupportedJwtException e) {
                String message = "지원되지 않는 JWT 토큰입니다.";
                log.error(message, e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
            } catch (Exception e) {
                String message = "Internal server error";
                log.error(message, e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Claims claims) {
        Long userId = Long.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);
        UserRole userRole = UserRole.of(claims.get("userRole", String.class));
        String nickname = claims.get("nickname", String.class);

        AuthUser authUser = new AuthUser(userId, email, userRole, nickname);
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
