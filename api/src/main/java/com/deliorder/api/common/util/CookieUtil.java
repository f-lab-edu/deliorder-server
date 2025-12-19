package com.deliorder.api.common.util;

import com.deliorder.api.common.constants.SecurityConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private CookieUtil() {}

    public static void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) SecurityConstants.REFRESH_TOKEN_TTL);
        response.addCookie(cookie);
    }
}
