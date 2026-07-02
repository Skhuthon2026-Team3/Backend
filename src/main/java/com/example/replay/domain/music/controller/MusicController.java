package com.example.replay.domain.music.controller;

import com.example.replay.common.response.ApiResponse;
import com.example.replay.domain.music.dto.MusicSearchResponse;
import com.example.replay.domain.music.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "음악", description = "음악 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    @Operation(summary = "iTunes 음악 검색", description = "키워드로 iTunes 음악을 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<List<MusicSearchResponse>> searchMusic(
            @Parameter(description = "검색할 음악 키워드", example = "NewJeans")
            @RequestParam(required = false) String term
    ) {
        return ApiResponse.success(musicService.searchMusic(term));
    }
}