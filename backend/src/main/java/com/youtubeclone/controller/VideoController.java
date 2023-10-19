package com.youtubeclone.controller;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.VideoRequest;
import com.youtubeclone.repository.VideoRepository;
import com.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file") MultipartFile file) {
        videoService.uploadVideo(file);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateVideo(@PathVariable String id, @RequestBody VideoRequest request) {

        try {
            return videoService.updateVideo(id, request);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return "Something went wrong";

    }
}

