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

        VideoResponse response = new VideoResponse();
        response.setVideoId(video.getId());
        response.setVideoStatus(video.getVideoStatus());
        response.setVideoUrl(video.getVideoUrl());
        response.setDescription(video.getDescription());
        response.setTitle(video.getTitle());
        response.setTags(video.getTags());
        response.setThumbnailUrl(video.getThumbnailUrl());

        return response;
    }
}
