package com.youtubeclone.service;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.VideoRequest;
import com.youtubeclone.payload.VideoResponse;
import com.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final S3Service s3Service;
    private final VideoRepository repository;
    private final UserService userService;

    @Override
    public VideoResponse uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        var savedVideo = repository.save(video);
        return new VideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    @Override
    public String updateVideo(String id, VideoRequest request) {
        Video video = findVideoById(id);
        video.setTitle(request.getTitle());
//        video.setVideoUrl(request.getVideoUrl());
        video.setDescription(request.getDescription());
        video.setTags(request.getTags());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setTitle(request.getTitle());
        video.setVideoStatus(request.getVideoStatus());
        video.setVideoStatus(request.getVideoStatus());
        repository.save(video);
        return "Video Successfully updated";
    }

    @Override
    public List<Video> getAllVideo() {
        return repository.findAll().stream().toList();
    }

    @Override
    public String addThumbnail(String videoId, MultipartFile file) {
        Video video = findVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        video.setThumbnailUrl(thumbnailUrl);
        repository.save(video);
        return thumbnailUrl;
    }

    public Video findVideoById(String videoId) {
        return repository.findById(videoId).orElseThrow(() -> new IllegalArgumentException(
                "Couldn't find any video with this id - " + videoId
        ));
    }

    @Override
    public VideoResponse getVideoById(String videoId) {
        Video video = findVideoById(videoId);

        increaseViewCount(video);
        userService.addVideoToHistory(videoId);
        return videoResponse(video);
    }

    private void increaseViewCount(Video video) {
        video.increaseViewCount();
        repository.save(video);
    }

    @Override
    public VideoResponse likeVideo(String videoId) {

        Video videoById = findVideoById(videoId);

        if (userService.ifLikedVideos(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDisLikedVideos(videoId)) {
            videoById.decrementLikes();
            userService.removeFromDisLikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideo(videoId);
        } else {
            videoById.incrementLikes();
            userService.addToLikedVideo(videoId);
        }

        repository.save(videoById);
        return videoResponse(videoById);

 }

    @Override
    public VideoResponse dislikeVideo(String videoId) {
        Video videoById = findVideoById(videoId);

        if (userService.ifDisLikedVideos(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        } else if (userService.ifLikedVideos(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDisLikes();
            userService.addToDisLikedVideo(videoId);
        } else {
            videoById.incrementDisLikes();
            userService.addToDisLikedVideo(videoId);
        }

        repository.save(videoById);

        return videoResponse(videoById);
 }

    private VideoResponse videoResponse(Video video) {
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setVideoId(video.getId());
        videoResponse.setTitle(video.getTitle());
        videoResponse.setThumbnailUrl(video.getThumbnailUrl());
        videoResponse.setViewCount(video.getViewCount().get());
        videoResponse.setTags(video.getTags());
        videoResponse.setDescription(video.getDescription());
        videoResponse.setVideoStatus(video.getVideoStatus());
        videoResponse.setVideoUrl(video.getVideoUrl());
        videoResponse.setLikedCount(video.getLikes().get());
        videoResponse.setDisLikedCount(video.getDislikes().get());
        return videoResponse;
    }


}
