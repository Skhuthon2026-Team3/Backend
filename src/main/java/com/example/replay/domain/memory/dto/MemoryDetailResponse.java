package com.example.replay.domain.memory.dto;

import com.example.replay.domain.memory.entity.Memory;
import java.time.LocalDateTime;

//각 게시물 상세 정보 조회
public record MemoryDetailResponse(
        Long memoryId,
        String title, //글 제목
        String content, // 글 본문
        String trackName, // 곡 제목
        String artistName, // 가수명
        String albumName, // 앨범명
        String artworkUrl, // 앨범 커버 url
        String previewUrl, // 미리듣기 url
        Boolean isPublic, //공개여부
        LocalDateTime createdAt //생성 시간
) {

    public static MemoryDetailResponse from(Memory memory) {
        return new MemoryDetailResponse(
                memory.getId(),
                memory.getTitle(),
                memory.getContent(),
                memory.getTrackName(),
                memory.getArtistName(),
                memory.getAlbumName(),
                memory.getArtworkUrl(),
                memory.getPreviewUrl(),
                memory.getIsPublic(),
                memory.getCreatedAt()
        );
    }
}