package com.example.replay.domain.memory.repository;

import com.example.replay.domain.memory.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
}