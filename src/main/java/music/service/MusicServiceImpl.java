package music.service;

import music.common.FileUtils;
import music.dto.MusicDto;
import music.dto.MusicFileDto;
import music.mapper.MusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<MusicDto> selectMusicList() {
        return musicMapper.selectMusicList();
    }

    @Override
    public List<MusicDto> selectMusicTitle(String lyrics) {
        return musicMapper.selectMusicTitle(lyrics);
    }

    @Override
    public void insertMusic(MusicDto musicDto, MultipartHttpServletRequest request) throws  Exception {
        musicMapper.insertMusic(musicDto);

        List<MusicFileDto> fileInfoList = fileUtils.parseFileInfo(musicDto.getMusicId(), request);

        if(!CollectionUtils.isEmpty(fileInfoList)) {
            musicMapper.insertMusicFileList(fileInfoList);
        }
    }

    @Override
    public MusicDto selectMusicDetail(int musicId) {
        MusicDto musicDto = musicMapper.selectMusicDetail(musicId);
        List<MusicFileDto> musicFileInfo = musicMapper.selectMusicFileList(musicId);
        musicDto.setFileInfoList(musicFileInfo);

        return musicDto;
    }

    @Override
    public void updateMusic(MusicDto musicDto) {
        musicMapper.updateMusic(musicDto);
    }

    @Override
    public void deleteMusic(int musicId) {
        musicMapper.deleteMusic(musicId);
    }

    @Override
    public MusicFileDto selectMusicFileInfo(int id, int musicId) {
        return musicMapper.selectMusicFileInfo(id, musicId);
    }

    @Override
    public void insertmusicWithFile(MusicDto musicDto, MultipartFile[] files) throws Exception {
        musicMapper.insertMusic(musicDto);
        List<MusicFileDto> fileInfoList = fileUtils.parseFileInfo(musicDto.getMusicId(), files);
        if (!CollectionUtils.isEmpty(fileInfoList)) {
            musicMapper.insertMusicFileList(fileInfoList);
        }
    }
}