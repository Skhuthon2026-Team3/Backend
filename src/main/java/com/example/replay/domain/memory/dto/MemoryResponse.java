package com.example.replay.domain.memory.dto;

import com.example.replay.domain.memory.entity.Memory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "추억 생성 응답")
public record MemoryResponse(
        @Schema(description = "추억 ID", example = "1")
        Long id,

        @Schema(description = "작성자 회원 ID", example = "1")
        Long memberId,

        @Schema(description = "작성자 닉네임", example = "replay-user")
        String nickname,

        @Schema(description = "추억 제목", example = "밤샘의 조용한 동반자")
        String title,

        @Schema(description = "곡 제목", example = "Story")
        String trackName,

        @Schema(description = "아티스트 이름", example = "릴러말즈 & TOIL")
        String artistName,

        @Schema(description = "앨범 이름", example = "TOYSTORY3")
        String albumName,

        @Schema(description = "앨범 커버 이미지 URL", example = "https://example.com/artwork.jpg")
        String artworkUrl,

        @Schema(description = "미리 듣기 오디오 URL", example = "https://example.com/preview.m4a")
        String previewUrl,

        @Schema(description = "추억 본문", example = "기말고사를 앞두고 책상에 앉아 있던 밤...")
        String content,

        @Schema(description = "AI가 다듬은 짧은 추억 문구", example = "AI가 다듬은 추억 본문")
        String aiStory,

        @Schema(description = "공개 여부", example = "true")
        Boolean isPublic,

        @Schema(description = "생성 시각", example = "2026-07-02T16:56:10.805275")
        LocalDateTime createdAt,

        @Schema(description = "수정 시각", example = "2026-07-02T17:10:30.123456")
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