package com.example.replay.domain.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "AI 추억 초안 생성 요청")
public record AiMemoryGenerateRequest(
        @Schema(description = "사용자가 선택한 곡 제목", example = "Ditto")
        @NotBlank
        String trackName,

        @Schema(description = "아티스트 이름", example = "NewJeans")
        @NotBlank
        String artistName,

        @Schema(description = "앨범 이름", nullable = true, example = "OMG")
        String albumName,

        @Schema(description = "앨범 커버 이미지 URL", nullable = true, example = "https://example.com/artwork.jpg")
        String artworkUrl,

        @Schema(description = "미리 듣기 오디오 URL", nullable = true, example = "https://example.com/preview.m4a")
        String previewUrl,

        @Schema(description = "사용자가 입력한 추억 힌트입니다. 최대 100자까지 입력할 수 있습니다.", example = "겨울밤 친구들과 들었던 노래")
        @NotBlank
        @Size(max = 100)
        String userInput,

        @Schema(description = "생성 목적입니다. MEMORY_CONTENT만 허용됩니다.", example = "MEMORY_CONTENT")
        @NotBlank
        String generationType
) {
}