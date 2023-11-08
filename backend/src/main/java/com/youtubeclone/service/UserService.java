package com.youtubeclone.service;

import java.util.Set;

public interface UserService {
    void registerUser(String tokenValue);

    void addToLikedVideo(String videoId);

    void addToDisLikedVideo(String videoId);

    public boolean ifLikedVideos(String videoId);
    public boolean ifDisLikedVideos(String videoId);


    void removeFromLikedVideos(String videoId);

    void removeFromDisLikedVideos(String videoId);

    void addVideoToHistory(String videoId);

    void subscribeToUser(String userId);

    void unSubscribeToUser(String userId);

    Set<String> getUserHistory(String userId);
}
