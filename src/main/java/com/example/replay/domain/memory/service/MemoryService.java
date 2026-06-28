package com.example.replay.domain.memory.service;

import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.repository.MemberRepository;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.entity.Memory;
import com.example.replay.domain.memory.repository.MemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemberRepository memberRepository;
    private final MemoryRepository memoryRepository;

    @Transactional
    public MemoryResponse createMemory(MemoryCreateRequest request) {
        Member member = getMember(request.memberId());

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Memory not found."));

        return MemoryDetailResponse.from(memory);
    }

    @Transactional(readOnly = true)
    public List<MemoryListResponse> getRecentPublicMemories() {
        return memoryRepository.findTop3ByIsPublicTrueOrderByCreatedAtDesc().stream()
                .map(MemoryListResponse::from)
                .toList();
    }

    @Transactional
    public void deleteMemory(Long memoryId, Long memberId) {
        Memory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Memory not found."));

        if (!memory.getMember().getId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the owner can delete this memory.");
        }

        memoryRepository.delete(memory);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found."));
    }
}