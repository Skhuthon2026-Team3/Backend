package com.example.replay.domain.auth.handler;

import com.example.replay.common.exception.ErrorCode;
import com.example.replay.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String AUTH_ERROR_CODE_ATTRIBUTE = "authErrorCode";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ErrorCode errorCode = resolveErrorCode(request);

        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), ErrorResponse.from(errorCode));
    }

    private ErrorCode resolveErrorCode(HttpServletRequest request) {
        Object errorCode = request.getAttribute(AUTH_ERROR_CODE_ATTRIBUTE);
        if (errorCode instanceof ErrorCode resolvedErrorCode) {
            return resolvedErrorCode;
        }
        return ErrorCode.UNAUTHORIZED;
    }
}