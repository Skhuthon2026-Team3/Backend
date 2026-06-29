package com.example.replay.domain.auth.dto;

import com.example.replay.domain.member.entity.SocialProvider;

public record SocialUserInfo(
        SocialProvider provider,
        String providerId,
        String email,
        String nickname,
        String profileImageUrl
) {
}