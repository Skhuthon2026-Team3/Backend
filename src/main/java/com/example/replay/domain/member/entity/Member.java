package com.example.replay.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "member",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_member_provider_provider_id",
                columnNames = {"provider", "provider_id"}
        )
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 아이디 (db에서 자동 매칭)

    @Column(length = 100)
    private String email; // 사용자 이메일

    @Column(length = 30)
    private String nickname; //닉네임

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SocialProvider provider; //소셜 로그인 제공 kakao, google 사용할 예정

    @Column(name = "provider_id", length = 100)
    private String providerId; // 소셜 로그인 제공자가 주는 고유 id

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; //회원생성

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; //업데이트 시간

    public Member(String email, String nickname, SocialProvider provider, String providerId) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
    }

    @PrePersist
    public void prePersist() { //db에 처음 저장되기 직전 실행된다.
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate //수정되기 직전 실행된다. 현재 시간으로 갱신된다.
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}