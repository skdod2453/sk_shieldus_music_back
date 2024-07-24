package music.service;

import music.dto.MusicDto;

import java.util.List;

public interface MusicService {
    List<MusicDto> selectMusicList();
    void insertMusic(MusicDto musicDto);
    MusicDto selectMusicDetail(int musicId);
    void updateMusic(MusicDto musicDto);
    void deleteMusic(int musicId);
}
