package com.example.replay.domain.auth.service;

import com.example.replay.domain.auth.dto.SocialUserInfo;
import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member loginOrRegister(SocialUserInfo userInfo) {
        return memberRepository.findByProviderAndProviderId(userInfo.provider(), userInfo.providerId())
                .map(member -> updateMember(member, userInfo))
                .orElseGet(() -> memberRepository.save(new Member(
                        userInfo.email(),
                        userInfo.nickname(),
                        userInfo.provider(),
                        userInfo.providerId(),
                        userInfo.profileImageUrl()
                )));
    }

    private Member updateMember(Member member, SocialUserInfo userInfo) {
        member.updateSocialProfile(userInfo.email(), userInfo.nickname(), userInfo.profileImageUrl());
        return member;
    }
}