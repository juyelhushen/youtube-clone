package com.youtubeclone.service;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.VideoRequest;
import com.youtubeclone.payload.VideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    public VideoResponse uploadVideo(MultipartFile file);

    String updateVideo(String id, VideoRequest request);

    List<Video> getAllVideo();

    String addThumbnail(String videoId, MultipartFile file);

    VideoResponse getVideoById(String videoId);

    VideoResponse likeVideo(String videoId);

    VideoResponse dislikeVideo(String videoId);
}
