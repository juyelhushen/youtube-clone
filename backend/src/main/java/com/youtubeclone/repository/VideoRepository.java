package com.youtubeclone.repository;

import com.youtubeclone.entity.Video;
import com.youtubeclone.payload.VideoResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video,String> {

    public Video findVideoByVideoUrl(String videoUrl);
}
