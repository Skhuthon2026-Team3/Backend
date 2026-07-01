package com.example.replay.domain.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "AI memory draft generation request")
public record AiMemoryGenerateRequest(
        @Schema(description = "Track name selected by the user", example = "Ditto")
        @NotBlank
        String trackName,

        @Schema(description = "Artist name", example = "NewJeans")
        @NotBlank
        String artistName,

        @Schema(description = "Album name", nullable = true, example = "OMG")
        String albumName,

        @Schema(description = "Artwork image URL", nullable = true)
        String artworkUrl,

        @Schema(description = "Preview audio URL", nullable = true)
        String previewUrl,

        @Schema(description = "User memory hint, up to 100 characters", example = "winter night with friends")
        @NotBlank
        @Size(max = 100)
        String userInput,

        @Schema(description = "Generation purpose. Only MEMORY_CONTENT is allowed.", example = "MEMORY_CONTENT")
        @NotBlank
        String generationType
) {
}
