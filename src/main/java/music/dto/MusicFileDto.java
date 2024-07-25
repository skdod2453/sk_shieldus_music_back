package music.dto;

import lombok.Data;

@Data
public class MusicFileDto {
    private int id;
    private int musicId;
    private String originalFileName;
    private String storedFilePath;
    private String fileSize;
    private String createdDatetime;
    private String updatedDatetime;

}
