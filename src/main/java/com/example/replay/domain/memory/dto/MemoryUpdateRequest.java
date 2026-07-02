package com.example.replay.domain.memory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Memory update request")
public record MemoryUpdateRequest(
        @Schema(description = "Memory title", example = "\uBC24\uC0D8\uC758 \uC870\uC6A9\uD55C \uB3D9\uBC18\uC790")
        @NotBlank
        @Size(max = 100)
        String title,

        @Schema(description = "Selected track name", example = "Story")
        @NotBlank
        String trackName,

        @Schema(description = "Selected artist name", example = "\uB9B4\uB7EC\uB9D0\uC988 & TOIL")
        @NotBlank
        String artistName,

        @Schema(description = "Selected album name", nullable = true, example = "TOYSTORY3")
        String albumName,

        @Schema(description = "Selected artwork image URL", nullable = true, example = "https://example.com/artwork.jpg")
        String artworkUrl,

        @Schema(description = "Selected preview audio URL", nullable = true, example = "https://example.com/preview.m4a")
        String previewUrl,

        @Schema(description = "Memory content", example = "\uAE30\uB9D0\uACE0\uC0AC\uB97C \uC55E\uB450\uACE0 \uCC45\uC0C1\uC5D0 \uC549\uC544 \uC788\uB358 \uBC24...")
        @NotBlank
        String content,

        @Schema(description = "AI-refined memory text", nullable = true, example = "AI\uAC00 \uB2E4\uB4EC\uC740 \uCD94\uC5B5 \uBCF8\uBB38")
        @Size(max = 100)
        String aiStory,

        @Schema(description = "Whether the memory is public", example = "true")
        @NotNull
        Boolean isPublic
) {
}