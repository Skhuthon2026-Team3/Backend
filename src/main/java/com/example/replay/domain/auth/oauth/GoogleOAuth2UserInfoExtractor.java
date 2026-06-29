package com.example.replay.domain.auth.oauth;

import com.example.replay.domain.auth.dto.SocialUserInfo;
import com.example.replay.domain.member.entity.SocialProvider;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    private static final String REGISTRATION_ID = "google";

    @Override
    public boolean supports(String registrationId) {
        return REGISTRATION_ID.equals(registrationId);
    }

    @Override
    public SocialUserInfo extract(Map<String, Object> attributes) {
        String providerId = String.valueOf(attributes.get("sub"));
        String email = value(attributes.get("email"));
        String nickname = value(attributes.get("name"));
        String profileImageUrl = value(attributes.get("picture"));

        return new SocialUserInfo(SocialProvider.GOOGLE, providerId, email, nickname, profileImageUrl);
    }

    private String value(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}