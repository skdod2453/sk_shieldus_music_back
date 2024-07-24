package music.controller;

import jakarta.servlet.http.HttpServletRequest;
import music.dto.MusicDto;
import music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @GetMapping("/music")
    public List<MusicDto> openMusicList(HttpServletRequest request) throws  Exception {
        return musicService.selectMusicList();
    }

    @PostMapping("/music/write")
    public void insertMusic(@RequestBody MusicDto musicDto) throws Exception {
        musicService.insertMusic(musicDto);
    }

    @GetMapping("/music/{musicId}")
    public MusicDto openMusicDetail(@PathVariable("musicId") int musicId) throws Exception {
        return musicService.selectMusicDetail(musicId);
    }

    @PutMapping("/music/{musicId}")
    public void updateMusic(@PathVariable("musicId") int musicId, @RequestBody MusicDto musicDto) throws  Exception {
        musicDto.setMusicId(musicId);
        musicService.updateMusic(musicDto);
    }

    @DeleteMapping("/music/{musicId}")
    public void deleteMusic(@PathVariable("musicId") int musicId) throws Exception {
        musicService.deleteMusic(musicId);
    }

}
