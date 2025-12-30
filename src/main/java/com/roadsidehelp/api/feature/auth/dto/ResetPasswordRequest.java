package com.roadsidehelp.api.feature.auth.dto;

public record ResetPasswordRequest(String token, String newPassword) {}
