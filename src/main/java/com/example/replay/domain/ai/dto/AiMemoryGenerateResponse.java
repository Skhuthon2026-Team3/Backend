package com.example.replay.domain.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI memory draft generation response")
public record AiMemoryGenerateResponse(
        @Schema(description = "Generated memory title", example = "The air of that day and a new beginning")
        String title,

        @Schema(description = "Generated memory content")
        String content
) {
}
