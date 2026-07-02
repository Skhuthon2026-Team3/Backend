package com.example.replay.domain.memory.repository;

import com.example.replay.domain.memory.entity.MemoryLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryLikeRepository extends JpaRepository<MemoryLike, Long> {

    boolean existsByMemberIdAndMemoryId(Long memberId, Long memoryId);

    Optional<MemoryLike> findByMemberIdAndMemoryId(Long memberId, Long memoryId);

    long countByMemoryId(Long memoryId);

    void deleteByMemoryId(Long memoryId);
}
