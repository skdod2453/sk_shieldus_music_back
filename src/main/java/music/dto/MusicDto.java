package music.dto;

import lombok.Data;

@Data
public class MusicDto {
    private int musicId;
    private String title;
    private String artist;
    private String lyrics;
    private String releaseDate;
    private String createdAt;
    private String updatedAt;

}
