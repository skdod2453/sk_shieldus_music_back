package music.mapper;

import music.dto.MusicDto;
import music.service.MusicService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MusicMapper {
    List<MusicDto> selectMusicList();
    void insertMusic(MusicDto musicDto);
    MusicDto selectMusicDetail(int musicId);
    void updateMusic(MusicDto musicDto);
    void deleteMusic(MusicDto musicDto);
}
