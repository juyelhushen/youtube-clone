package com.youtubeclone.payload;

import com.youtubeclone.entity.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {

    private String videoId;
    private String title;
    private String description;
    private Set<String> tags;
    private VideoStatus videoStatus;
    private String thumbnailUrl;
    private String videoUrl;
    private Integer likedCount;
    private Integer disLikedCount;
    private Integer viewCount;

    public VideoResponse(String videoId, String videoUrl) {
        this.videoId = videoId;
        this.videoUrl = videoUrl;
    }
}
