package com.youtubeclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
@Document(value = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String sub;
    private String profileUrl;
    private String channelName;
    private String channelDescription;
    private Set<String> subscribeToUser = ConcurrentHashMap.newKeySet();
    private Set<String> subscribers = ConcurrentHashMap.newKeySet();
    private Set<String> videoHistory = ConcurrentHashMap.newKeySet();
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> disLikedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> likedComment = ConcurrentHashMap.newKeySet();
    private Set<String> disLikedComment = ConcurrentHashMap.newKeySet();

    public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void addToDisLikedVideos(String videoId) {
        disLikedVideos.add(videoId);
    }


    public void removeFromLikedVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDisLikedVideos(String videoId) {
        disLikedVideos.remove(videoId);
    }

    public void addToLikedComment(String commentId) {
        likedComment.add(commentId);
    }

    public void addToDisLikedComment(String commentId) {
        disLikedComment.add(commentId);
    }


    public void removeFromLikedComment(String commentId) {
        likedComment.remove(commentId);
    }

    public void removeFromDisLikedComment(String commentId) {
        disLikedComment.remove(commentId);
    }


    public void addVideoToHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void addSubscribedToUser(String userId) {
        subscribeToUser.add(userId);
    }

    public void addSubscriber(String userId) {
        subscribers.add(userId);
    }
    public void removeFromSubscribedToUser(String userId) {
        subscribeToUser.remove(userId);
    }

    public void removeSubscriber(String userid) {
        subscribers.remove(userid);
    }

}
