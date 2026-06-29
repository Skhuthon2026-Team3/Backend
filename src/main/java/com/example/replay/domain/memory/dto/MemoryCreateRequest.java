package com.example.replay.domain.memory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemoryCreateRequest(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        String trackName,

        @NotBlank
        String artistName,

        String albumName,

        String artworkUrl,

        String previewUrl,

        @NotBlank
        String content,

        @Size(max = 100)
        String aiStory,

        @NotNull
        Boolean isPublic
) {
}
