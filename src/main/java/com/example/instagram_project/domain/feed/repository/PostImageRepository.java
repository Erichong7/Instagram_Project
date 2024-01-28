package com.example.instagram_project.domain.feed.repository;

import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteAllByPost(Post post);
    List<PostImage> findAllByPost(Post post);
    void deleteByPostAndImage(Post post, String url);
}
