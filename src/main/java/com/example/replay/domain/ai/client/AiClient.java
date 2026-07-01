package com.example.replay.domain.ai.client;

import com.example.replay.common.exception.BusinessException;
import com.example.replay.common.exception.ErrorCode;
import com.example.replay.domain.ai.dto.AiMemoryGenerateRequest;
import com.example.replay.domain.ai.dto.AiMemoryGenerateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
public class AiClient {

    private static final String GENERATE_PATH = "/api/v1/generate";
    private static final int CONNECT_TIMEOUT_MILLIS = 3_000;
    private static final int READ_TIMEOUT_MILLIS = 20_000;

    private final RestClient restClient;

    public AiClient(@Value("${ai.server.url}") String aiServerUrl) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
        requestFactory.setReadTimeout(READ_TIMEOUT_MILLIS);

        this.restClient = RestClient.builder()
                .baseUrl(aiServerUrl)
                .requestFactory(requestFactory)
                .build();
    }

    public AiMemoryGenerateResponse generateMemory(AiMemoryGenerateRequest request) {
        try {
            return restClient.post()
                    .uri(GENERATE_PATH)
                    .body(request)
                    .retrieve()
                    .body(AiMemoryGenerateResponse.class);
        } catch (ResourceAccessException exception) {
            throw new BusinessException(ErrorCode.AI_SERVER_TIMEOUT, exception);
        } catch (RestClientResponseException exception) {
            throw new BusinessException(ErrorCode.AI_SERVER_ERROR, exception);
        } catch (RestClientException exception) {
            throw new BusinessException(ErrorCode.AI_SERVER_ERROR, exception);
        }
    }
}
