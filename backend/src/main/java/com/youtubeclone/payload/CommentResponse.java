package com.youtubeclone.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private String id;
    private String authorId;
    private String authorName;
    private String commentText;
    private Integer likeCount;
    private Integer disLikeCount;
    private LocalDate date;
}
