package com.example.replay.domain.memory.dto;

import com.example.replay.domain.memory.entity.Memory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "추억 목록 응답")
public record MemoryListResponse(
        @Schema(description = "추억 ID", example = "1")
        Long memoryId,

        @Schema(description = "추억 제목", example = "밤샘의 조용한 동반자")
        String title,

        @Schema(description = "곡 제목", example = "Story")
        String trackName,

        @Schema(description = "아티스트 이름", example = "릴러말즈 & TOIL")
        String artistName,

        @Schema(description = "앨범 커버 이미지 URL", example = "https://example.com/artwork.jpg")
        String artworkUrl,

        @Schema(description = "공개 여부", example = "true")
        Boolean isPublic,

        @Schema(description = "생성 시각", example = "2026-07-02T16:56:10.805275")
        LocalDateTime createdAt,

        @Schema(description = "좋아요 수", example = "3")
        long likeCount,

        @Schema(description = "현재 사용자의 좋아요 여부", example = "true")
        boolean likedByMe
) {

    public static MemoryListResponse from(Memory memory) {
        return from(memory, 0, false);
    }

    public static MemoryListResponse from(Memory memory, long likeCount, boolean likedByMe) {
        return new MemoryListResponse(
                memory.getId(),
                memory.getTitle(),
                memory.getTrackName(),
                memory.getArtistName(),
                memory.getArtworkUrl(),
                memory.getIsPublic(),
                memory.getCreatedAt(),
                likeCount,
                likedByMe
        );
    }
}