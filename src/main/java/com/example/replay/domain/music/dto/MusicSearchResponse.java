package com.example.replay.domain.music.dto;

public record MusicSearchResponse(
        String trackName,
        String artistName,
        String albumName,
        String artworkUrl,
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
