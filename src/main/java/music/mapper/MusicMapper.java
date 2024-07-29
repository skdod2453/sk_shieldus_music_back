package music.mapper;

import music.dto.MusicDto;
import music.dto.MusicFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicMapper {
    List<MusicDto> selectMusicList();
    List<MusicDto> selectMusicTitle(String lyrics);
    void insertMusic(MusicDto musicDto);
    MusicDto selectMusicDetail(int musicId);
    void updateMusic(MusicDto musicDto);
    void deleteMusic(int musicId);
    void insertMusicFileList(List<MusicFileDto> fileInfoList);
    List<MusicFileDto> selectMusicFileList(int musicId);
    MusicFileDto selectMusicFileInfo(@Param("id") int id, @Param("musicId") int musicId);
}
