package com.example.replay.domain.memory.dto;

import com.example.replay.domain.memory.entity.Memory;
import java.time.LocalDateTime;

public record MemoryResponse(
        Long id,
        Long memberId,
        String nickname,
        String title,
        String trackName,
        String artistName,
        String albumName,
        String artworkUrl,
        String previewUrl,
        String content,
        String aiStory,
        Boolean isPublic,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static MemoryResponse from(Memory memory) {
        return new MemoryResponse(
                memory.getId(),
                memory.getMember().getId(),
                memory.getMember().getNickname(),
                memory.getTitle(),
                memory.getTrackName(),
                memory.getArtistName(),
                memory.getAlbumName(),
                memory.getArtworkUrl(),
                memory.getPreviewUrl(),
                memory.getContent(),
                memory.getAiStory(),
                memory.getIsPublic(),
                memory.getCreatedAt(),
                memory.getUpdatedAt()
        );
    }
}