package com.example.replay.domain.auth.service;

import com.example.replay.domain.auth.dto.SocialUserInfo;
import com.example.replay.domain.auth.oauth.OAuth2UserInfoExtractor;
import com.example.replay.domain.auth.principal.OAuth2MemberPrincipal;
import com.example.replay.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialLoginService socialLoginService;
    private final List<OAuth2UserInfoExtractor> userInfoExtractors;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String nameAttributeKey = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        SocialUserInfo userInfo = extractUserInfo(registrationId, oauth2User);
        Member member = socialLoginService.loginOrRegister(userInfo);

        return new OAuth2MemberPrincipal(
                member.getId(),
                nameAttributeKey,
                oauth2User.getAttributes(),
                oauth2User.getAuthorities()
        );
    }

    private SocialUserInfo extractUserInfo(String registrationId, OAuth2User oauth2User) {
        return userInfoExtractors.stream()
                .filter(extractor -> extractor.supports(registrationId))
                .findFirst()
                .orElseThrow(() -> new OAuth2AuthenticationException("Unsupported social provider: " + registrationId))
                .extract(oauth2User.getAttributes());
    }
}