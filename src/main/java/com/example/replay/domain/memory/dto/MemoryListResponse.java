package com.example.replay.domain.memory.dto;

import com.example.replay.domain.memory.entity.Memory;
import java.time.LocalDateTime;

//사용자별, 게시글 목록 조회
public record MemoryListResponse(
        Long memoryId,
        String title, //제목
        String trackName, // 노래 제목
        String artistName, // 가수 명
        String artworkUrl, // 앨범 커버 url
        Boolean isPublic, //공개 여부
        LocalDateTime createdAt //만든 시간
) {

    public static MemoryListResponse from(Memory memory) {
        return new MemoryListResponse(
                memory.getId(),
                memory.getTitle(),
                memory.getTrackName(),
                memory.getArtistName(),
                memory.getArtworkUrl(),
                memory.getIsPublic(),
                memory.getCreatedAt()
        );
    }
}