package com.example.replay.domain.music.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItunesSearchResponse(
        Integer resultCount,
        List<ItunesTrackResponse> results
) {
}
