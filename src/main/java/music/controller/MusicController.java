package music.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import music.dto.MusicDto;
import music.dto.MusicFileDto;
import music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @GetMapping("/music")
    public List<MusicDto> openMusicList(HttpServletRequest request) throws Exception {
        return musicService.selectMusicList();
    }

    @GetMapping("/music/lyrics/{lyrics}")
    public List<MusicDto> openMusicList( @PathVariable("lyrics") String lyrics, HttpServletRequest request) throws Exception {
        return musicService.selectMusicTitle(lyrics);
    }

    @PostMapping("/music/write")
    public void insertMusic(@RequestPart("data") MusicDto musicDto, @RequestPart("files") MultipartFile[] files) throws Exception {
        musicService.insertmusicWithFile(musicDto, files);
    }

    @GetMapping("/music/{musicId}")
    public MusicDto openMusicDetail(@PathVariable("musicId") int musicId) throws Exception {
        return musicService.selectMusicDetail(musicId);
    }

    @PutMapping("/music/{musicId}")
    public void updateMusic(@PathVariable("musicId") int musicId, @RequestBody MusicDto musicDto) throws Exception {
        musicDto.setMusicId(musicId);
        musicService.updateMusic(musicDto);
    }

    @DeleteMapping("/music/{musicId}")
    public void deleteMusic(@PathVariable("musicId") int musicId) throws Exception {
        musicService.deleteMusic(musicId);
    }

    @GetMapping("/music/file/{musicId}/{id}")
    public void downloadMusicFile(@PathVariable("id") int id, @PathVariable("musicId") int musicId, HttpServletResponse response) throws Exception {
        MusicFileDto musicFileDto = musicService.selectMusicFileInfo(id, musicId);
        if (ObjectUtils.isEmpty(musicFileDto)) return;
        Path path = Paths.get(musicFileDto.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(musicFileDto.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}