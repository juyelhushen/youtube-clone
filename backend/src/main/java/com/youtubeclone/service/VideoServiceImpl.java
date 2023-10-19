package com.youtubeclone.service;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.VideoRequest;
import com.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final S3Service s3Service;
    private final VideoRepository repository;

    @Override
    public void uploadVideo(MultipartFile file) {
        String videoUrl  = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        repository.save(video);
    }

    @Override
    public String updateVideo(String id, VideoRequest request) {
        Video video = repository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "No Video is exit with this is id" + id
        ));
        video.setTitle(request.getTitle());
//        video.setVideoUrl(request.getVideoUrl());
        video.setDescription(request.getDescription());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setTitle(request.getTitle());
        video.setVideoStatus(request.getVideoStatus());
        video.setVideoStatus(request.getVideoStatus());
        repository.save(video);
        return "Video Successfully updated";
    }
}
