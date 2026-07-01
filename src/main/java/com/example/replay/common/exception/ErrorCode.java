package com.example.replay.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    MEMORY_NOT_FOUND(HttpStatus.NOT_FOUND, "추억을 찾을 수 없습니다."),
    FORBIDDEN_MEMORY_ACCESS(HttpStatus.FORBIDDEN, "해당 추억에 접근할 권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    INVALID_AI_GENERATION_TYPE(HttpStatus.BAD_REQUEST, "AI generationType은 MEMORY_CONTENT이여야 합니다."),
    AI_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "AI 서버 요청이 실패하였습니다."),
    AI_SERVER_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "AI 서버가 타임 아웃입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

