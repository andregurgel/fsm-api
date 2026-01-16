package dev.andregurgel.fsm_api.controller.dto;

public record AuthTokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String scope
) {
}

