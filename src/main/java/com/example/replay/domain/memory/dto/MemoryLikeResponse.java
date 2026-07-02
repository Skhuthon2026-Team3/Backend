package com.example.replay.domain.memory.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "추억 좋아요 응답")
public record MemoryLikeResponse(
        @Schema(description = "추억 ID", example = "1")
        Long memoryId,

        @Schema(description = "추억의 전체 좋아요 수", example = "3")
        long likeCount,

        @Schema(description = "현재 사용자가 좋아요를 눌렀는지 여부", example = "true")
        boolean likedByMe
) {
}