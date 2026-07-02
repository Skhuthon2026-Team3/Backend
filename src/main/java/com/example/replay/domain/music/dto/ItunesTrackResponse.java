package com.example.replay.domain.music.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//iTunes API 원본 응답 받기용 DTO, 노래 제목, 가수 이름, 앨범이름, 앨범 커버, 미리듣기30초 필드 5개만 받는다.
//실제 가공한 클래스는 MusicSearchResponse이다.
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItunesTrackResponse(
        String trackName, //노래제목
        String artistName, //가수 이름
        String collectionName, //itunes에서 사용하는 앨범 제목 필드, -> albumName으로 가공 처리하였음
        String artworkUrl100, // 앨범 커버 이미지 url, 기본 100x100이고, 사이즈 크기 조절은 url 마지막 숫자를 바꾸면 된다(클 수록 고화질 가능하다)
        String previewUrl //미리 듣기 30초 url,
) {
}