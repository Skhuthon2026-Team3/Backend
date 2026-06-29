package com.example.replay.domain.music.service;

import com.example.replay.common.exception.BusinessException;
import com.example.replay.common.exception.ErrorCode;
import com.example.replay.domain.music.dto.ItunesSearchResponse;
import com.example.replay.domain.music.dto.MusicSearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

@Service
public class MusicService {

    private static final String ITUNES_SEARCH_URL = "https://itunes.apple.com/search";
    private static final int DEFAULT_LIMIT = 5;

    private final ObjectMapper objectMapper;
    private final RestClient restClient = RestClient.create();

    public MusicService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MusicSearchResponse> searchMusic(String term) {
        if (!StringUtils.hasText(term)) {
            return Collections.emptyList();
        }

        String responseBody = restClient.get()
                .uri(ITUNES_SEARCH_URL, uriBuilder -> buildSearchUri(uriBuilder, term))
                .retrieve()
                .body(String.class);

        ItunesSearchResponse response = parseItunesResponse(responseBody);

        if (response == null || response.results() == null) {
            return Collections.emptyList();
        }

        return response.results().stream()
                .map(MusicSearchResponse::from)
                .toList();
    }

    private ItunesSearchResponse parseItunesResponse(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return null;
        }

        try {
            return objectMapper.readValue(responseBody, ItunesSearchResponse.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private URI buildSearchUri(UriBuilder uriBuilder, String term) {
        return uriBuilder
                .queryParam("term", term)
                .queryParam("country", "KR")
                .queryParam("media", "music")
                .queryParam("limit", DEFAULT_LIMIT)
                .build();
    }
}