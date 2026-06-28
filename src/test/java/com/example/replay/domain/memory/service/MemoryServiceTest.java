package com.example.replay.domain.memory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.entity.SocialProvider;
import com.example.replay.domain.member.repository.MemberRepository;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryDetailResponse;
import com.example.replay.domain.memory.dto.MemoryListResponse;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.repository.MemoryRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class MemoryServiceTest {

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemoryRepository memoryRepository;

    @Test
    void createMemory() {
        Member member = memberRepository.save(
                new Member("memory-test@replay.com", "memory-test", SocialProvider.GOOGLE, "memory-test-provider-id")
        );

        MemoryCreateRequest request = createRequest(member.getId());

        MemoryResponse response = memoryService.createMemory(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.memberId()).isEqualTo(member.getId());
        assertThat(response.nickname()).isEqualTo("memory-test");
        assertThat(response.title()).isEqualTo("Discharge day");
        assertThat(response.trackName()).isEqualTo("Wonder");
        assertThat(response.artistName()).isEqualTo("ADOY");
        assertThat(response.albumName()).isEqualTo("LOVE");
        assertThat(response.artworkUrl()).isEqualTo("https://example.com/artwork.jpg");
        assertThat(response.previewUrl()).isEqualTo("https://example.com/preview.m4a");
        assertThat(response.content()).isEqualTo("A song I listened to on the KTX after discharge.");
        assertThat(response.aiStory()).isNull();
        assertThat(response.isPublic()).isTrue();
        assertThat(memoryRepository.existsById(response.id())).isTrue();
    }

    @Test
    void getMyMemories() {
        Member member = memberRepository.save(
                new Member("list-test@replay.com", "list-test", SocialProvider.GOOGLE, "list-test-provider-id")
        );
        MemoryResponse response = memoryService.createMemory(createRequest(member.getId()));

        List<MemoryListResponse> memories = memoryService.getMyMemories(member.getId());

        assertThat(memories).extracting(MemoryListResponse::memoryId).contains(response.id());
        assertThat(memories).anySatisfy(memory -> {
            assertThat(memory.title()).isEqualTo("Discharge day");
            assertThat(memory.trackName()).isEqualTo("Wonder");
            assertThat(memory.artistName()).isEqualTo("ADOY");
            assertThat(memory.artworkUrl()).isEqualTo("https://example.com/artwork.jpg");
            assertThat(memory.isPublic()).isTrue();
            assertThat(memory.createdAt()).isNotNull();
        });
    }

    @Test
    void getMyMemoryDetail() {
        Member member = memberRepository.save(
                new Member("detail-test@replay.com", "detail-test", SocialProvider.GOOGLE, "detail-test-provider-id")
        );
        MemoryResponse response = memoryService.createMemory(createRequest(member.getId()));

        MemoryDetailResponse detail = memoryService.getMyMemoryDetail(response.id(), member.getId());

        assertThat(detail.memoryId()).isEqualTo(response.id());
        assertThat(detail.title()).isEqualTo("Discharge day");
        assertThat(detail.content()).isEqualTo("A song I listened to on the KTX after discharge.");
        assertThat(detail.trackName()).isEqualTo("Wonder");
        assertThat(detail.artistName()).isEqualTo("ADOY");
        assertThat(detail.albumName()).isEqualTo("LOVE");
        assertThat(detail.artworkUrl()).isEqualTo("https://example.com/artwork.jpg");
        assertThat(detail.previewUrl()).isEqualTo("https://example.com/preview.m4a");
        assertThat(detail.isPublic()).isTrue();
        assertThat(detail.createdAt()).isNotNull();
    }

    @Test
    void getMyMemoryDetailNotFoundWhenMemberIsNotOwner() {
        Member owner = memberRepository.save(
                new Member("detail-owner@replay.com", "detail-owner", SocialProvider.GOOGLE, "detail-owner-provider-id")
        );
        Member other = memberRepository.save(
                new Member("detail-other@replay.com", "detail-other", SocialProvider.GOOGLE, "detail-other-provider-id")
        );
        MemoryResponse response = memoryService.createMemory(createRequest(owner.getId()));

        assertThatThrownBy(() -> memoryService.getMyMemoryDetail(response.id(), other.getId()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND");
    }

    @Test
    void deleteMemory() {
        Member member = memberRepository.save(
                new Member("delete-test@replay.com", "delete-test", SocialProvider.GOOGLE, "delete-test-provider-id")
        );
        MemoryResponse response = memoryService.createMemory(createRequest(member.getId()));

        memoryService.deleteMemory(response.id(), member.getId());

        assertThat(memoryRepository.existsById(response.id())).isFalse();
    }

    @Test
    void deleteMemoryForbiddenWhenMemberIsNotOwner() {
        Member owner = memberRepository.save(
                new Member("owner-test@replay.com", "owner-test", SocialProvider.GOOGLE, "owner-test-provider-id")
        );
        Member other = memberRepository.save(
                new Member("other-test@replay.com", "other-test", SocialProvider.GOOGLE, "other-test-provider-id")
        );
        MemoryResponse response = memoryService.createMemory(createRequest(owner.getId()));

        assertThatThrownBy(() -> memoryService.deleteMemory(response.id(), other.getId()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("403 FORBIDDEN");

        assertThat(memoryRepository.existsById(response.id())).isTrue();
    }

    private MemoryCreateRequest createRequest(Long memberId) {
        return new MemoryCreateRequest(
                memberId,
                "Discharge day",
                "Wonder",
                "ADOY",
                "LOVE",
                "https://example.com/artwork.jpg",
                "https://example.com/preview.m4a",
                "A song I listened to on the KTX after discharge.",
                null,
                true
        );
    }
}