package com.youtubeclone.service;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.CommentRequest;
import com.youtubeclone.payload.CommentResponse;
import com.youtubeclone.payload.VideoRequest;
import com.youtubeclone.payload.VideoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    public VideoResponse uploadVideo(MultipartFile file);

    String updateVideo(String id, VideoRequest request);

    List<VideoResponse> getAllVideos();

    String addThumbnail(String videoId, MultipartFile file);

    VideoResponse getVideoById(String videoId);

    VideoResponse likeVideo(String videoId);

    VideoResponse dislikeVideo(String videoId);

    String addComment(String videoId, CommentRequest request);

    List<CommentResponse> getAllComment(String videoId);
}
