package com.example.replay.domain.member.repository;

import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.entity.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndProviderId(SocialProvider provider, String providerId);
}