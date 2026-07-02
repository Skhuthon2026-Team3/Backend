package com.example.replay.domain.memory.entity;

import com.example.replay.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memory")
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(name = "track_name", nullable = false)
    private String trackName;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "artwork_url", length = 500)
    private String artworkUrl;

    @Column(name = "preview_url", length = 500)
    private String previewUrl;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "ai_story", length = 100) // AI story text is limited to 100 characters
    private String aiStory;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Memory(
            Member member,
            String title,
            String trackName,
            String artistName,
            String albumName,
            String artworkUrl,
            String previewUrl,
            String content,
            String aiStory,
            Boolean isPublic
    ) {
        this.member = member;
        this.title = title;
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.artworkUrl = artworkUrl;
        this.previewUrl = previewUrl;
        this.content = content;
        this.aiStory = aiStory;
        this.isPublic = isPublic;
    }

    public void update(
            String title,
            String trackName,
            String artistName,
            String albumName,
            String artworkUrl,
            String previewUrl,
            String content,
            String aiStory,
            Boolean isPublic
    ) {
        this.title = title;
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.artworkUrl = artworkUrl;
        this.previewUrl = previewUrl;
        this.content = content;
        this.aiStory = aiStory;
        this.isPublic = isPublic;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}