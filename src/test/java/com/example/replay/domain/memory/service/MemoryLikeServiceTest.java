package com.example.replay.domain.memory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.replay.common.exception.BusinessException;
import com.example.replay.common.exception.ErrorCode;
import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.entity.SocialProvider;
import com.example.replay.domain.member.repository.MemberRepository;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryLikeResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoryLikeServiceTest {

    @Autowired
    private MemoryLikeService memoryLikeService;

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void likePublicMemory() {
        Member member = saveMember("like-success");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("Public memory", true));

        MemoryLikeResponse response = memoryLikeService.likeMemory(member.getId(), memory.id());

        assertThat(response.memoryId()).isEqualTo(memory.id());
        assertThat(response.likeCount()).isEqualTo(1);
        assertThat(response.likedByMe()).isTrue();
    }

    @Test
    void likeMemoryAlreadyLiked() {
        Member member = saveMember("like-duplicate");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("Duplicate memory", true));
        memoryLikeService.likeMemory(member.getId(), memory.id());

        assertThatThrownBy(() -> memoryLikeService.likeMemory(member.getId(), memory.id()))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.ALREADY_LIKED_MEMORY);
    }

    @Test
    void unlikeMemory() {
        Member member = saveMember("unlike-success");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("Unlike memory", true));
        memoryLikeService.likeMemory(member.getId(), memory.id());

        MemoryLikeResponse response = memoryLikeService.unlikeMemory(member.getId(), memory.id());

        assertThat(response.memoryId()).isEqualTo(memory.id());
        assertThat(response.likeCount()).isZero();
        assertThat(response.likedByMe()).isFalse();
    }

    @Test
    void unlikeMemoryNotFound() {
        Member member = saveMember("unlike-not-found");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("No like memory", true));

        assertThatThrownBy(() -> memoryLikeService.unlikeMemory(member.getId(), memory.id()))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMORY_LIKE_NOT_FOUND);
    }

    @Test
    void likePrivateMemoryForbidden() {
        Member member = saveMember("like-private");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("Private memory", false));

        assertThatThrownBy(() -> memoryLikeService.likeMemory(member.getId(), memory.id()))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.FORBIDDEN_PRIVATE_MEMORY_LIKE);
    }

    @Test
    void likeMemoryNotFound() {
        Member member = saveMember("like-missing");

        assertThatThrownBy(() -> memoryLikeService.likeMemory(member.getId(), 999999L))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMORY_NOT_FOUND);
    }

    @Test
    void getLikeStatus() {
        Member member = saveMember("status-member");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("Status memory", true));
        memoryLikeService.likeMemory(member.getId(), memory.id());

        MemoryLikeResponse response = memoryLikeService.getLikeStatus(member.getId(), memory.id());

        assertThat(response.likeCount()).isEqualTo(1);
        assertThat(response.likedByMe()).isTrue();
    }

    @Test
    void memoryResponsesIncludeLikeStatus() {
        Member member = saveMember("response-member");
        MemoryResponse memory = memoryService.createMemory(member.getId(), createRequest("Response memory", true));
        memoryLikeService.likeMemory(member.getId(), memory.id());

        MemoryDetailResponse detail = memoryService.getPublicMemoryDetail(memory.id(), member.getId());
        List<MemoryListResponse> memories = memoryService.getRecentPublicMemories(member.getId());

        assertThat(detail.likeCount()).isEqualTo(1);
        assertThat(detail.likedByMe()).isTrue();
        assertThat(memories).anySatisfy(item -> {
            if (item.memoryId().equals(memory.id())) {
                assertThat(item.likeCount()).isEqualTo(1);
                assertThat(item.likedByMe()).isTrue();
            }
        });
    }

    private Member saveMember(String key) {
        return memberRepository.save(
                new Member(key + "@replay.com", key, SocialProvider.GOOGLE, key + "-provider-id")
        );
    }

    private MemoryCreateRequest createRequest(String title, Boolean isPublic) {
        return new MemoryCreateRequest(
                title,
                "Wonder",
                "ADOY",
                "LOVE",
                "https://example.com/artwork.jpg",
                "https://example.com/preview.m4a",
                "A song I listened to on the KTX after discharge.",
                null,
                isPublic
        );
    }
}
