package com.example.replay.domain.auth.handler;

import com.example.replay.domain.auth.dto.AuthTokenResponse;
import com.example.replay.domain.auth.jwt.JwtTokenProvider;
import com.example.replay.domain.auth.principal.OAuth2MemberPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2MemberPrincipal principal = (OAuth2MemberPrincipal) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(principal.getMemberId());
        AuthTokenResponse tokenResponse = AuthTokenResponse.bearer(
                accessToken,
                principal.getMemberId(),
                principal.getEmail()
        );

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}
