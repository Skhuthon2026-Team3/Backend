package com.example.replay.domain.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI 추억 초안 생성 응답")
public record AiMemoryGenerateResponse(
        @Schema(description = "AI가 생성한 추억 제목", example = "그날의 공기와 새로운 시작")
        String title,

        @Schema(description = "AI가 생성한 추억 본문", example = "그 노래를 들으면 친구들과 함께 걷던 겨울밤이 떠오릅니다.")
        String content
) {
}