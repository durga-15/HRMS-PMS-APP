package com.hrms.pms.pms_app.pms.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
public class CookieService {
    private final String refreshTokenCookieName;
    private final boolean cookieHttpOnly;
    private final boolean cookieSecure;
//    private final int cookieMaxAge;
    private final String cookieDomain;
    private final String cookieSameSite;

    public CookieService(@Value("${jwt.refresh-cookie-name}") String refreshTokenCookieName,
                         @Value(("${jwt.cookie-http-only}")) boolean cookieHttpOnly,
                         @Value("${jwt.cookie-secure}") boolean cookieSecure,
                         @Value("${jwt.cookie-domain}") String cookieDomain,
                         @Value("${jwt.cookie-same-site}") String cookieSameSite) {
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.cookieHttpOnly = cookieHttpOnly;
        this.cookieSecure = cookieSecure;
        this.cookieDomain = normalizeCookieDomain(cookieDomain);
        this.cookieSameSite = normalizeSameSite(cookieSameSite);
    }

    //Create method to attach cookie to response.

    public void attachRefreshCookie(HttpServletResponse response, String value, int maxAge){

        var responseCookieBuilder = ResponseCookie.from(refreshTokenCookieName, value)
                .httpOnly(cookieHttpOnly)
                .secure(cookieSecure)
                .path("/")
                .maxAge(maxAge)
                .sameSite(cookieSameSite);

        if (cookieDomain != null && !cookieDomain.isBlank()){
            responseCookieBuilder.domain(cookieDomain);
        }

        ResponseCookie responseCookie = responseCookieBuilder.build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    public void clearResponseCookie(HttpServletResponse response){
        var builder = ResponseCookie.from(refreshTokenCookieName, "")
                .maxAge(0)
                .httpOnly(cookieHttpOnly)
                .path("/")
                .sameSite(cookieSameSite)
                .secure(cookieSecure);

        if (cookieDomain != null && !cookieDomain.isBlank()){
            builder.domain(cookieDomain);
        }

        ResponseCookie responseCookie = builder.build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

    }

    public void noStoreHeaders(HttpServletResponse response){
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader("pragma", "no-cache");
    }

    private String normalizeCookieDomain(String rawCookieDomain) {
        if (rawCookieDomain == null) {
            return null;
        }

        String normalizedDomain = rawCookieDomain.trim();
        if (normalizedDomain.isEmpty()
                || "null".equalsIgnoreCase(normalizedDomain)
                || "localhost".equalsIgnoreCase(normalizedDomain)
                || normalizedDomain.contains("://")
                || normalizedDomain.contains("/")
                || normalizedDomain.contains(":")) {
            return null;
        }

        return normalizedDomain;
    }

    private String normalizeSameSite(String rawSameSite) {
        if (rawSameSite == null || rawSameSite.isBlank()) {
            return "Lax";
        }

        return switch (rawSameSite.trim().toLowerCase()) {
            case "strict" -> "Strict";
            case "none" -> "None";
            default -> "Lax";
        };
    }
}
