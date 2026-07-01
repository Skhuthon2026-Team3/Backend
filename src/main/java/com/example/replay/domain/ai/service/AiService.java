package com.example.replay.domain.ai.service;

import com.example.replay.common.exception.BusinessException;
import com.example.replay.common.exception.ErrorCode;
import com.example.replay.domain.ai.client.AiClient;
import com.example.replay.domain.ai.dto.AiMemoryGenerateRequest;
import com.example.replay.domain.ai.dto.AiMemoryGenerateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AiService {

    private static final String MEMORY_CONTENT_GENERATION_TYPE = "MEMORY_CONTENT";

    private final AiClient aiClient;

    public AiMemoryGenerateResponse generateMemory(AiMemoryGenerateRequest request) {
        validateGenerationType(request.generationType());

        AiMemoryGenerateResponse response = aiClient.generateMemory(request);
        if (response == null || !StringUtils.hasText(response.title()) || !StringUtils.hasText(response.content())) {
            throw new BusinessException(ErrorCode.AI_SERVER_ERROR);
        }

        return response;
    }

    private void validateGenerationType(String generationType) {
        if (!MEMORY_CONTENT_GENERATION_TYPE.equals(generationType)) {
            throw new BusinessException(ErrorCode.INVALID_AI_GENERATION_TYPE);
        }
    }
}
