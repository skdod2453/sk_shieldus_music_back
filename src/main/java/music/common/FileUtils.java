package music.common;

import music.dto.MusicFileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    public List<MusicFileDto> parseFileInfo(int musicId, MultipartHttpServletRequest request) throws Exception {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }

        List<MusicFileDto> fileInfoList = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime now = ZonedDateTime.now();
        String storedDir = uploadDir + now.format(dtf);
        File dir = new File(storedDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        Iterator<String> fileTagNames = request.getFileNames();
        while (fileTagNames.hasNext()) {
            String fileTagName = fileTagNames.next();
            List<MultipartFile> files = request.getFiles(fileTagName);
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String contentType = file.getContentType();
                if (ObjectUtils.isEmpty(contentType)) continue;

                String fileExtension = "";
                if (contentType.contains("jpeg")) {
                    fileExtension = ".jpg";
                } else if (contentType.contains("png")) {
                    fileExtension = ".png";
                } else if (contentType.contains("gif")) {
                    fileExtension = ".gif";
                } else continue;

                String storedFileName = System.nanoTime() + fileExtension;

                MusicFileDto dto = new MusicFileDto();
                dto.setMusicId(musicId);
                dto.setFileSize("" + file.getSize());
                dto.setOriginalFileName(file.getOriginalFilename());
                dto.setStoredFilePath(storedDir + "/" + storedFileName);
                fileInfoList.add(dto);

                dir = new File(storedDir + "/" + storedFileName);
                file.transferTo(dir);
            }
        }
        return fileInfoList;
    }
    public List<MusicFileDto> parseFileInfo(int musicId, MultipartFile[] files) throws Exception {

        if (ObjectUtils.isEmpty(files)) {
            return null;
        }

        List<MusicFileDto> fileInfoList = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime now = ZonedDateTime.now();
        String storedDir = uploadDir + now.format(dtf);
        File dir = new File(storedDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)) continue;

            String fileExtension = "";
            if (contentType.contains("jpeg")) {
                fileExtension = ".jpg";
            } else if (contentType.contains("png")) {
                fileExtension = ".png";
            } else if (contentType.contains("gif")) {
                fileExtension = ".gif";
            } else {
                continue;
            }

            String storedFileName = System.nanoTime() + fileExtension;

            MusicFileDto dto = new MusicFileDto();
            dto.setFileSize("" + file.getSize());
            dto.setOriginalFileName(file.getOriginalFilename());
            dto.setStoredFilePath(storedDir + "/" + storedFileName);
            fileInfoList.add(dto);

            dir = new File(storedDir + "/" + storedFileName);
            file.transferTo(dir);
        }
        return fileInfoList;
    }
}
