package com.example.replay.domain.music.controller;

import com.example.replay.common.response.ApiResponse;
import com.example.replay.domain.music.dto.MusicSearchResponse;
import com.example.replay.domain.music.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Music", description = "Music search API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    @Operation(summary = "Search iTunes music", description = "Search music tracks from iTunes by keyword.")
    @GetMapping("/search")
    public ApiResponse<List<MusicSearchResponse>> searchMusic(@RequestParam(required = false) String term) {
        return ApiResponse.success(musicService.searchMusic(term));
    }
}