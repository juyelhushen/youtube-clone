package com.youtubeclone.repository;

import com.youtubeclone.entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VideoRepository extends MongoRepository<Video,String> {

    public Video findVideoByVideoUrl(String videoUrl);

    @Query("{'tags': {$in: ?0}}")
    Set<Video> findByTags(Set<String> tags);

    @Query("{userId: ?0}")
    Set<Video> findVideosByUserId(String userId);

}
