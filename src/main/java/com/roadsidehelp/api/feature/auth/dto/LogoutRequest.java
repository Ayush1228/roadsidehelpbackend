package com.roadsidehelp.api.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank(message = "Refresh token must not be empty")
        String refreshToken
) {}

