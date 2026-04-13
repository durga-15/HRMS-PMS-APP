package com.hrms.pms.pms_app.pms.security;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class CookieServiceTest {

    @Test
    void attachRefreshCookieOmitsInvalidConfiguredDomain() {
        CookieService cookieService = new CookieService("refreshToken", true, false, "null", "lax");
        MockHttpServletResponse response = new MockHttpServletResponse();

        cookieService.attachRefreshCookie(response, "token-value", 60);

        assertThat(response.getHeader(HttpHeaders.SET_COOKIE))
                .contains("refreshToken=token-value")
                .doesNotContain("Domain=");
    }

    @Test
    void clearRefreshCookieOmitsLocalhostDomain() {
        CookieService cookieService = new CookieService("refreshToken", true, false, "localhost", "lax");
        MockHttpServletResponse response = new MockHttpServletResponse();

        cookieService.clearResponseCookie(response);

        assertThat(response.getHeader(HttpHeaders.SET_COOKIE))
                .contains("refreshToken=")
                .doesNotContain("Domain=");
    }

    @Test
    void attachRefreshCookieKeepsValidConfiguredDomain() {
        CookieService cookieService = new CookieService("refreshToken", true, true, "app.example.com", "none");
        MockHttpServletResponse response = new MockHttpServletResponse();

        cookieService.attachRefreshCookie(response, "token-value", 60);

        assertThat(response.getHeader(HttpHeaders.SET_COOKIE))
                .contains("Domain=app.example.com")
                .contains("Secure")
                .contains("SameSite=None");
    }

    @Test
    void attachRefreshCookieNormalizesSameSiteValue() {
        CookieService cookieService = new CookieService("refreshToken", true, true, "", "NONE");
        MockHttpServletResponse response = new MockHttpServletResponse();

        cookieService.attachRefreshCookie(response, "token-value", 60);

        assertThat(response.getHeader(HttpHeaders.SET_COOKIE))
                .contains("SameSite=None");
    }
}
