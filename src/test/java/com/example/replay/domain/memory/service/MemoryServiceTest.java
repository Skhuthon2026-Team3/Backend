package com.example.replay.domain.memory.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.replay.domain.member.entity.Member;
import com.example.replay.domain.member.entity.SocialProvider;
import com.example.replay.domain.member.repository.MemberRepository;
import com.example.replay.domain.memory.dto.MemoryCreateRequest;
import com.example.replay.domain.memory.dto.MemoryResponse;
import com.example.replay.domain.memory.repository.MemoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        MemoryCreateRequest request = new MemoryCreateRequest(
                member.getId(),
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
}