package com.youtubeclone.service;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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

    Long getTotalComment(String videoId);

    String deleteComment(CommentUpdateRequest request);

    Set<VideoResponse> getUserAllLikedVideos(String userId);

    Set<VideoResponse> getUserHistory(String userId);

    Set<VideoResponse> suggestedVideoByTags(String videoId);


    CommentResponse likeComment(CommentUpdateRequest request);

    CommentResponse disLikeComment(CommentUpdateRequest request);
}
