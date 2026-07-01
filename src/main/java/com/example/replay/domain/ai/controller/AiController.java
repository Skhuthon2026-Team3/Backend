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

@Tag(name = "AI", description = "AI memory generation API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Generate AI memory draft",
            description = "Generate a memory title and content from selected music information and user input. "
                    + "This API does not save a Memory."
    )
    @PostMapping("/memories/generate")
    public ApiResponse<AiMemoryGenerateResponse> generateMemory(
            @AuthenticationPrincipal Long memberId,
            @Valid @RequestBody AiMemoryGenerateRequest request
    ) {
        return ApiResponse.success(aiService.generateMemory(request));
    }
}