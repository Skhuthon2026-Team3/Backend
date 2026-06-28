package com.example.replay.domain.memory.repository;

import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.memory.entity.Memory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

    List<Memory> findByMemberOrderByCreatedAtDesc(Member member);

    Optional<Memory> findByIdAndMember(Long id, Member member);

    List<Memory> findTop3ByIsPublicTrueOrderByCreatedAtDesc();
}