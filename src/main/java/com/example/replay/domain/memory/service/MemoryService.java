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
                .map(MemoryListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemoryDetailResponse getMyMemoryDetail(Long memoryId, Long memberId) {
        Member member = getMember(memberId);

        Memory memory = memoryRepository.findByIdAndMember(memoryId, member)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        return MemoryDetailResponse.from(memory);
    }

    @Transactional(readOnly = true)
    public MemoryDetailResponse getPublicMemoryDetail(Long memoryId) {
        Memory memory = memoryRepository.findByIdAndIsPublicTrue(memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        return MemoryDetailResponse.from(memory);
    }

    @Transactional(readOnly = true)
    public List<MemoryListResponse> getRecentPublicMemories() {
        return memoryRepository.findTop3ByIsPublicTrueOrderByCreatedAtDesc().stream()
                .map(MemoryListResponse::from)
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

        return MemoryDetailResponse.from(memory);
    }

    @Transactional
    public void deleteMemory(Long memoryId, Long memberId) {
        Memory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMORY_NOT_FOUND));

        if (!memory.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_MEMORY_ACCESS);
        }

        memoryRepository.delete(memory);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}