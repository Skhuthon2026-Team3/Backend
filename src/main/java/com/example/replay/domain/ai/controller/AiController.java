package com.example.replay.domain.ai.controller;

import com.example.replay.common.response.ApiResponse;
import com.example.replay.domain.ai.dto.AiMemoryGenerateRequest;
import com.example.replay.domain.ai.dto.AiMemoryGenerateResponse;
import com.example.replay.domain.ai.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI", description = "AI 추억 생성 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "AI 추억 초안 생성",
            description = "선택한 음악 정보와 사용자의 입력을 바탕으로 추억 제목과 본문 초안을 생성합니다. 이 API는 추억을 저장하지 않습니다."
    )
    @PostMapping("/memories/generate")
    public ApiResponse<AiMemoryGenerateResponse> generateMemory(
            @AuthenticationPrincipal Long memberId,
            @Valid @RequestBody AiMemoryGenerateRequest request
    ) {
        return ApiResponse.success(aiService.generateMemory(request));
    }
}