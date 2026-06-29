package com.example.replay.domain.auth.oauth;

import com.example.replay.domain.auth.dto.SocialUserInfo;
import java.util.Map;

public interface OAuth2UserInfoExtractor {

    boolean supports(String registrationId);

    SocialUserInfo extract(Map<String, Object> attributes);
}