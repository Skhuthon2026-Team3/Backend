package com.example.replay.domain.music.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItunesTrackResponse(
        String trackName,
        String artistName,
        String collectionName,
        String artworkUrl100,
        String previewUrl
) {
}
