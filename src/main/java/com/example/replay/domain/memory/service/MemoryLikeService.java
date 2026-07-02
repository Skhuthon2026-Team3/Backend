package com.example.replay.domain.memory.service;

import com.example.replay.common.exception.BusinessException;
import com.example.replay.common.exception.ErrorCode;
import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.repository.MemberRepository;
import com.example.replay.domain.memory.dto.MemoryLikeResponse;
import com.example.replay.domain.memory.entity.Memory;
import com.example.replay.domain.memory.entity.MemoryLike;
import com.example.replay.domain.memory.repository.MemoryLikeRepository;
import com.example.replay.domain.memory.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoryLikeService {

    private final MemberRepository memberRepository;
    private final MemoryRepository memoryRepository;
    private final MemoryLikeRepository memoryLikeRepository;

    @Transactional
    public MemoryLikeResponse likeMemory(Long memberId, Long memoryId) {
        Member member = getMember(memberId);
        Memory memory = getMemory(memoryId);
        validatePublicMemory(memory);

        if (memoryLikeRepository.existsByMemberIdAndMemoryId(memberId, memoryId)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED_MEMORY);
        }

        memoryLikeRepository.save(new MemoryLike(member, memory));
        return new MemoryLikeResponse(memoryId, memoryLikeRepository.countByMemoryId(memoryId), true);
    }

    @Transactional
    public MemoryLikeResponse unlikeMemory(Long memberId, Long memoryId) {
        getMember(memberId);
        getMemory(memoryId);

        MemoryLike memoryLike = memoryLikeRepository.findByMemberIdAndMemoryId(memberId, memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_LIKE_NOT_FOUND));

        memoryLikeRepository.delete(memoryLike);
        return new MemoryLikeResponse(memoryId, memoryLikeRepository.countByMemoryId(memoryId), false);
    }

    @Transactional(readOnly = true)
    public MemoryLikeResponse getLikeStatus(Long memberId, Long memoryId) {
        getMemory(memoryId);

        long likeCount = memoryLikeRepository.countByMemoryId(memoryId);
        boolean likedByMe = memberId != null && memoryLikeRepository.existsByMemberIdAndMemoryId(memberId, memoryId);
        return new MemoryLikeResponse(memoryId, likeCount, likedByMe);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Memory getMemory(Long memoryId) {
        return memoryRepository.findById(memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));
    }

    private void validatePublicMemory(Memory memory) {
        if (!Boolean.TRUE.equals(memory.getIsPublic())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_PRIVATE_MEMORY_LIKE);
        }
    }
}
