package com.example.replay.domain.member.init;

import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.entity.SocialProvider;
import com.example.replay.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestMemberInitializer implements CommandLineRunner {

    private static final String TEST_PROVIDER_ID = "replay-test-member";

    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) {
        memberRepository.findByProviderAndProviderId(SocialProvider.GOOGLE, TEST_PROVIDER_ID)
                .orElseGet(() -> memberRepository.save(
                        new Member("test@replay.com", "test-user", SocialProvider.GOOGLE, TEST_PROVIDER_ID)
                ));
    }
}