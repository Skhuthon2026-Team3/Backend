package com.example.replay.domain.auth.dto;

public record AuthTokenResponse(
        String tokenType,
        String accessToken,
        Long memberId
) {

    public static AuthTokenResponse bearer(String accessToken, Long memberId) {
        return new AuthTokenResponse("Bearer", accessToken, memberId);
    }
}