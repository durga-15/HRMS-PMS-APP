package com.hrms.pms.pms_app.pms.dtos;

import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @Size(max = 4096, message = "Refresh token is too long")
        String refreshToken
) {
}
