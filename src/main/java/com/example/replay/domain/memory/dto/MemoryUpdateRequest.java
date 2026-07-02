package com.example.replay.domain.memory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "추억 수정 요청")
public record MemoryUpdateRequest(
        @Schema(description = "추억 제목", example = "밤샘의 조용한 동반자")
        @NotBlank
        @Size(max = 100)
        String title,

        @Schema(description = "선택한 곡 제목", example = "Story")
        @NotBlank
        String trackName,

        @Schema(description = "선택한 아티스트 이름", example = "릴러말즈 & TOIL")
        @NotBlank
        String artistName,

        @Schema(description = "선택한 앨범 이름", nullable = true, example = "TOYSTORY3")
        String albumName,

        @Schema(description = "선택한 앨범 커버 이미지 URL", nullable = true, example = "https://example.com/artwork.jpg")
        String artworkUrl,

        @Schema(description = "선택한 미리 듣기 오디오 URL", nullable = true, example = "https://example.com/preview.m4a")
        String previewUrl,

        @Schema(description = "추억 본문", example = "기말고사를 앞두고 책상에 앉아 있던 밤...")
        @NotBlank
        String content,

        @Schema(description = "AI가 다듬은 짧은 추억 문구", nullable = true, example = "AI가 다듬은 추억 본문")
        @Size(max = 100)
        String aiStory,

        @Schema(description = "공개 여부", example = "true")
        @NotNull
        Boolean isPublic
) {
}