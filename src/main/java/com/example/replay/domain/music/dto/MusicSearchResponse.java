package com.example.replay.domain.music.dto;

//실제 응답을 우리 필드로 이름 바꿔서 짰다.
public record MusicSearchResponse(
        String trackName, //곡 제목
        String artistName,// 가수 명
        String albumName, //앨범 명
        String artworkUrl,// 앨범 커버
        String previewUrl // 미리듣기
) {

    public static MusicSearchResponse from(ItunesTrackResponse response) {
        return new MusicSearchResponse(
                response.trackName(),
                response.artistName(),
                response.collectionName(),
                response.artworkUrl100(),
                response.previewUrl()
        );
    }
}
