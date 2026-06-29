package com.example.replay.domain.auth.oauth;

import com.example.replay.domain.auth.dto.SocialUserInfo;
import com.example.replay.domain.member.entity.SocialProvider;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    private static final String REGISTRATION_ID = "kakao";

    @Override
    public boolean supports(String registrationId) {
        return REGISTRATION_ID.equals(registrationId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SocialUserInfo extract(Map<String, Object> attributes) {
        String providerId = String.valueOf(attributes.get("id"));
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.getOrDefault("kakao_account", Map.of());
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.getOrDefault("profile", Map.of());

        String email = value(kakaoAccount.get("email"));
        String nickname = value(profile.get("nickname"));
        String profileImageUrl = value(profile.get("profile_image_url"));

        return new SocialUserInfo(SocialProvider.KAKAO, providerId, email, nickname, profileImageUrl);
    }

    private String value(Object value) {
        return value == null ? null : String.valueOf(value);
    }
}