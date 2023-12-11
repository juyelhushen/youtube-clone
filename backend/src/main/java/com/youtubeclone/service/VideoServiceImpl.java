package com.youtubeclone.service;

import com.youtubeclone.entity.Comment;
import com.youtubeclone.entity.User;
import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.*;
import com.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        video.setUserId(request.getUserId());
//        video.setVideoUrl(request.getVideoUrl());
        video.setDescription(request.getDescription());
        video.setTags(request.getTags());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setTitle(request.getTitle());
        video.setVideoStatus(request.getVideoStatus());
        video.setVideoStatus(request.getVideoStatus());
        video.setUploadOn(LocalDateTime.now());
        repository.save(video);
        return "Video Successfully updated";
    }

    @Override
    public List<VideoResponse> getAllVideos() {
        return repository.findAll().stream().map(this::videoResponse).toList();
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

        if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDisLikes();
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

        if (userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
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
        videoResponse.setUserId(video.getUserId());
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
        videoResponse.setUploadOn(video.getUploadOn());
        return videoResponse;
    }

    @Override
    public String addComment(String videoId, CommentRequest request) {
        Video video = findVideoById(videoId);
        Comment comment = new Comment();

        String commentId = UUID.randomUUID().toString();
        comment.setId(commentId);
        comment.setAuthorId(request.getAuthorId());
        comment.setText(request.getCommentText());
        comment.setDate(LocalDate.now());
        video.addComment(comment);
        repository.save(video);
        return "Your comment successfully added.";
    }


    @Override
    public List<CommentResponse> getAllComment(String videoId) {
        Video video = findVideoById(videoId);
        List<Comment> commentList = video.getComments();
        return commentList.stream().map(this::getMappedComments).toList();
    }

    @Override
    public Long getTotalComment(String videoId) {
        Video video = findVideoById(videoId);
        long totalComments = video.getComments().size();
        return totalComments;
    }

    @Override
    public String deleteComment(CommentUpdateRequest request) {
        Video video = findVideoById(request.getVideoId());
        Optional<Comment> comment = video.getComments().stream().filter(
                c -> c.getId().equals(request.getCommentId())
        ).findFirst();
        video.removeComment(comment.get());
        repository.save(video);
        return "Deleted Successfully";

    }

    @Override
    public Set<VideoResponse> getUserAllLikedVideos(String userId) {
        User user = userService.getUser(userId);
        Set<String> videoIds = user.getLikedVideos();
        return videosByIds(videoIds);
    }

    @Override
    public Set<VideoResponse> getUserHistory(String userId) {
        User user = userService.getUser(userId);
        Set<String> videoIds = user.getVideoHistory();
        return videosByIds(videoIds);
    }

    private Set<VideoResponse> videosByIds(Set<String> videoId) {
        return repository.findAllById(videoId).stream().map(this::videoResponse)
                .collect(Collectors.toSet());
    }

    private Set<VideoResponse> videosByTags(Set<String> tags) {
        Set<Video> filteredVideos = repository.findByTags(tags);
        return filteredVideos.stream()
                .map(this::videoResponse)
                .collect(Collectors.toSet());
    }

//    private Set<VideoResponse> videosByTags(Set<String> tags) {
//        return repository.findAll().stream()
//                .filter(v -> v.getTags().stream().anyMatch(tags::contains))
//                .map(this::videoResponse)
//                .collect(Collectors.toSet());
//    }


    private CommentResponse getMappedComments(Comment comment) {
        CommentResponse response = new CommentResponse();
        User user = userService.getUser(comment.getAuthorId());
        response.setAuthorId(comment.getAuthorId());
        if (user != null) {
            response.setAuthorName(user.getFullName());
        }
        response.setCommentText(comment.getText());
        response.setId(comment.getId());
        response.setLikeCount(comment.getLikeCount());
        response.setDisLikeCount(comment.getDisLikeCount());
        response.setDate(comment.getDate());
        return response;
    }

    @Override
    public Set<VideoResponse> suggestedVideoByTags(String videoId) {
        Video video = findVideoById(videoId);
        Set<String> tags = video.getTags();
        return videosByTags(tags);
    }

    @Override
    public CommentResponse likeComment(CommentUpdateRequest request) {
        Video videoById = findVideoById(request.getVideoId());

        Comment comment = videoById.getComments().stream()
                .filter(c -> c.getId().equals(request.getCommentId())).findFirst()
                .orElseThrow(() -> new IllegalStateException("No comments found"));

        if (userService.ifLikedComment(request.getCommentId())) {
            comment.decrementLikes();
            userService.removeFromLikedComment(request.getCommentId());
        } else if (userService.ifDisLikedComment(request.getCommentId())) {
            comment.decrementDisLikes();
            userService.removeFromDisLikedComment(request.getCommentId());
            comment.incrementLikes();
            userService.addToLikedComment(request.getCommentId());
        } else {
            comment.incrementLikes();
            userService.addToLikedComment(request.getCommentId());
        }

        repository.save(videoById);
        return getMappedComments(comment);
    }

    @Override
    public CommentResponse disLikeComment(CommentUpdateRequest request) {
        Video videoById = findVideoById(request.getVideoId());
        Comment comment = videoById.getComments().stream()
                .filter(c -> c.getId().equals(request.getCommentId()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Comment Not Found"));

        if (userService.ifDisLikedComment(request.getCommentId())) {
            comment.decrementDisLikes();
            userService.removeFromDisLikedComment(request.getCommentId());
        } else if (userService.ifLikedComment(request.getCommentId())) {
            comment.decrementLikes();
            userService.removeFromLikedComment(request.getCommentId());
            comment.incrementDisLikes();
            userService.addToDisLikedComment(request.getCommentId());
        } else {
            comment.incrementDisLikes();
            userService.addToDisLikedComment(request.getCommentId());
        }
        repository.save(videoById);
        return getMappedComments(comment);
    }

    @Override
    public Set<VideoResponse> getVideoByUserId(String userId) {
        Set<Video> videos = repository.findVideosByUserId(userId);
        return videos.stream().map(this::videoResponse).collect(Collectors.toSet());
    }
}
