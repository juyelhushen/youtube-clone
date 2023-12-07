package com.youtubeclone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String id;
    private String text;
    private String authorId;
    private AtomicInteger likeCount =  new AtomicInteger(0);
    private AtomicInteger disLikeCount = new AtomicInteger(0);
    private LocalDate date;

    public void incrementLikes() {
        likeCount.incrementAndGet();
    }
    public void decrementLikes() {
        likeCount.decrementAndGet();
    }
    public void incrementDisLikes() {
        disLikeCount.incrementAndGet();
    }
    public void decrementDisLikes() {
        disLikeCount.decrementAndGet();
    }

}
