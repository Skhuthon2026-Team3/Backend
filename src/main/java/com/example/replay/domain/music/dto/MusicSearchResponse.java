package com.example.replay.domain.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음악 검색 응답")
public record MusicSearchResponse(
        @Schema(description = "곡 제목", example = "Ditto")
        String trackName,

        @Schema(description = "아티스트 이름", example = "NewJeans")
        String artistName,

        @Schema(description = "앨범 이름", example = "OMG")
        String albumName,

        @Schema(description = "앨범 커버 이미지 URL", example = "https://example.com/artwork.jpg")
        String artworkUrl,

        @Schema(description = "미리 듣기 오디오 URL", example = "https://example.com/preview.m4a")
        String previewUrl
) {

    public static MusicSearchResponse from(ItunesTrackResponse response) {
        return new MusicSearchResponse(
                response.trackName(),
                response.artistName(),
                response.collectionName(),
                response.artworkUrl100(),
                response.previewUrl()
        );
    }
}