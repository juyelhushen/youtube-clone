package com.youtubeclone.controller;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.*;
import com.youtubeclone.repository.VideoRepository;
import com.youtubeclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/videos")
//@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.uploadVideo(file);
    }

    @PutMapping("/update/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateVideo(@PathVariable String videoId, @RequestBody VideoRequest request) {

        try {
            return videoService.updateVideo(videoId, request);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return "Something went wrong";

    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String addThumbnail(@RequestParam("videoId") String videoId,
                               @RequestParam("file") MultipartFile file) {
        return videoService.addThumbnail(videoId, file);
    }

    @GetMapping("/{videoId}")
    public VideoResponse getVideoById(@PathVariable String videoId) {
        return videoService.getVideoById(videoId);
    }

    @PostMapping("/like/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoResponse likeVideo(@PathVariable String videoId) {
        return videoService.likeVideo(videoId);
    }

    @PostMapping("/dislike/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoResponse dislikeVideo(@PathVariable String videoId) {
        return videoService.dislikeVideo(videoId);
    }

    @PostMapping("/addcomment/{videoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String addComment(@PathVariable String videoId, @RequestBody CommentRequest request) {
        return videoService.addComment(videoId, request);
    }

    @GetMapping("/comments/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComment(@PathVariable String videoId) {
        return videoService.getAllComment(videoId);
    }

    @GetMapping("/comments/count/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public Long getTotalComment(@PathVariable String videoId) {
        return videoService.getTotalComment(videoId);
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestBody CommentDeleteRequest request) {
        return videoService.deleteComment(request);
    }


    @GetMapping("/getall")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoResponse> getAllVideo() {
        return videoService.getAllVideos();

    }

    @DeleteMapping("/deleteall")
    public void deleteAllvideo() {
        videoRepository.deleteAll();
    }
}

