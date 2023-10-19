package com.youtubeclone.service;

import com.youtubeclone.payload.VideoRequest;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    public void uploadVideo(MultipartFile file);

    String updateVideo(String id, VideoRequest request);
}
