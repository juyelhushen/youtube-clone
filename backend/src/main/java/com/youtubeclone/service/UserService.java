package com.youtubeclone.service;

import com.youtubeclone.entity.User;
import com.youtubeclone.payload.VideoResponse;

import java.util.List;
import java.util.Set;

public interface UserService {

    String registerUser(String tokenValue);

    void addToLikedVideo(String videoId);

    void addToDisLikedVideo(String videoId);

    void addToLikedComment(String commentId);

    void addToDisLikedComment(String commentId);

    public boolean ifLikedVideo(String videoId);
    public boolean ifDisLikedVideo(String videoId);

    public boolean ifLikedComment(String commentId);
    public boolean ifDisLikedComment(String commentId);


    void removeFromLikedVideos(String videoId);

    void removeFromDisLikedVideos(String videoId);

    void removeFromLikedComment(String commentId);

    void removeFromDisLikedComment(String commentId);

    void addVideoToHistory(String videoId);

    Boolean subscribeToUser(String userId);

    Boolean unSubscribeToUser(String userId);

    Set<String> getUserHistory(String userId);
    public User getUser(String userId);

//    Set<VideoResponse> getUserAllLikedVideos(String userId);
}
