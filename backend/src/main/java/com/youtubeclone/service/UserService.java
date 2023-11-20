package com.youtubeclone.service;

import com.youtubeclone.entity.User;

import java.util.Set;

public interface UserService {
    String registerUser(String tokenValue);

    void addToLikedVideo(String videoId);

    void addToDisLikedVideo(String videoId);

    public boolean ifLikedVideo(String videoId);
    public boolean ifDisLikedVideo(String videoId);


    void removeFromLikedVideos(String videoId);

    void removeFromDisLikedVideos(String videoId);

    void addVideoToHistory(String videoId);

    Boolean subscribeToUser(String userId);

    Boolean unSubscribeToUser(String userId);

    Set<String> getUserHistory(String userId);
    public User getUser(String userId);
}
