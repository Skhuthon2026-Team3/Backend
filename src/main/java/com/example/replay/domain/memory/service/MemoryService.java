package com.example.replay.domain.memory.service;

import com.example.replay.common.exception.BusinessException;
import com.example.replay.common.exception.ErrorCode;
import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.repository.MemberRepository;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.dto.MemoryUpdateRequest;
import com.example.replay.domain.memory.entity.Memory;
import com.example.replay.domain.memory.repository.MemoryLikeRepository;
import com.example.replay.domain.memory.repository.MemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemberRepository memberRepository;
    private final MemoryRepository memoryRepository;
    private final MemoryLikeRepository memoryLikeRepository;

    @Transactional
    public MemoryResponse createMemory(Long memberId, MemoryCreateRequest request) {
        Member member = getMember(memberId);

        Memory memory = new Memory(
                member,
                request.title(),
                request.trackName(),
                request.artistName(),
                request.albumName(),
                request.artworkUrl(),
                request.previewUrl(),
                request.content(),
                request.aiStory(),
                request.isPublic()
        );

        Memory savedMemory = memoryRepository.save(memory);
        return MemoryResponse.from(savedMemory);
    }

    @Transactional(readOnly = true)
    public List<MemoryListResponse> getMyMemories(Long memberId) {
        Member member = getMember(memberId);

        return memoryRepository.findByMemberOrderByCreatedAtDesc(member).stream()
                .map(memory -> toListResponse(memory, memberId))
                .toList();
    }

    @Transactional(readOnly = true)
    public MemoryDetailResponse getMyMemoryDetail(Long memoryId, Long memberId) {
        Member member = getMember(memberId);

        Memory memory = memoryRepository.findByIdAndMember(memoryId, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        return toDetailResponse(memory, memberId);
    }

    @Transactional(readOnly = true)
    public MemoryDetailResponse getPublicMemoryDetail(Long memoryId) {
        return getPublicMemoryDetail(memoryId, null);
    }

    @Transactional(readOnly = true)
    public MemoryDetailResponse getPublicMemoryDetail(Long memoryId, Long memberId) {
        Memory memory = memoryRepository.findByIdAndIsPublicTrue(memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        return toDetailResponse(memory, memberId);
    }

    @Transactional(readOnly = true)
    public List<MemoryListResponse> getRecentPublicMemories() {
        return getRecentPublicMemories(null);
    }

    @Transactional(readOnly = true)
    public List<MemoryListResponse> getRecentPublicMemories(Long memberId) {
        return memoryRepository.findTop8ByIsPublicTrueOrderByCreatedAtDesc().stream()
                .map(memory -> toListResponse(memory, memberId))
                .toList();
    }

    @Transactional
    public MemoryDetailResponse updateMemory(Long memberId, Long memoryId, MemoryUpdateRequest request) {
        Memory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        if (!memory.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_MEMORY_ACCESS);
        }

        memory.update(
                request.title(),
                request.trackName(),
                request.artistName(),
                request.albumName(),
                request.artworkUrl(),
                request.previewUrl(),
                request.content(),
                request.aiStory(),
                request.isPublic()
        );

        return toDetailResponse(memory, memberId);
    }

    @Transactional
    public void deleteMemory(Long memoryId, Long memberId) {
        Memory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        if (!memory.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_MEMORY_ACCESS);
        }

        memoryLikeRepository.deleteByMemoryId(memoryId);
        memoryRepository.delete(memory);
    }

    private MemoryListResponse toListResponse(Memory memory, Long memberId) {
        return MemoryListResponse.from(
                memory,
                memoryLikeRepository.countByMemoryId(memory.getId()),
                isLikedByMember(memberId, memory.getId())
        );
    }

    private MemoryDetailResponse toDetailResponse(Memory memory, Long memberId) {
        return MemoryDetailResponse.from(
                memory,
                memoryLikeRepository.countByMemoryId(memory.getId()),
                isLikedByMember(memberId, memory.getId())
        );
    }

    private boolean isLikedByMember(Long memberId, Long memoryId) {
        return memberId != null && memoryLikeRepository.existsByMemberIdAndMemoryId(memberId, memoryId);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}