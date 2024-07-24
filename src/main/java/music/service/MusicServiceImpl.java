package music.service;

import music.dto.MusicDto;
import music.mapper.MusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Override
    public List<MusicDto> selectMusicList() {
        return musicMapper.selectMusicList();
    }

    @Override
    public void insertMusic(MusicDto musicDto) {
        musicMapper.insertMusic(musicDto);
    }

    @Override
    public MusicDto selectMusicDetail(int musicId) {
        return musicMapper.selectMusicDetail(musicId);
    }

    @Override
    public void updateMusic(MusicDto musicDto) {
        musicMapper.updateMusic(musicDto);
    }

    @Override
    public void deleteMusic(int musicId) {
        MusicDto musicDto = new MusicDto();
        musicDto.setMusicId(musicId);
        musicMapper.deleteMusic(musicDto);
    }
}
