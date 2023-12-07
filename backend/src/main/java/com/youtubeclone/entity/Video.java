package com.youtubeclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@Data
@Builder
@Document(value = "video")
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger dislikes = new AtomicInteger(0);
    private Set<String> tags = ConcurrentHashMap.newKeySet();
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<Comment> comments = new CopyOnWriteArrayList<>();
    private LocalDateTime uploadOn;


    public void incrementLikes() {
        likes.incrementAndGet();
    }
    public void decrementLikes() {
        likes.decrementAndGet();
    }
    public void incrementDisLikes() {
        dislikes.incrementAndGet();
    }
    public void decrementDisLikes() {
        dislikes.decrementAndGet();
    }
    public void increaseViewCount() {
        viewCount.incrementAndGet();
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}
