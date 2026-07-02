package com.example.replay.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 토큰 응답")
public record AuthTokenResponse(
        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,

        @Schema(description = "JWT 액세스 토큰", example = "JWT")
        String accessToken,

        @Schema(description = "회원 ID", example = "6")
        Long memberId,

        @Schema(description = "회원 이메일", example = "user@gmail.com")
        String email
) {

    public static AuthTokenResponse bearer(String accessToken, Long memberId, String email) {
        return new AuthTokenResponse("Bearer", accessToken, memberId, email);
    }
}
