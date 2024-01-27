package com.example.instagram_project.domain.feed.repository;

import com.example.instagram_project.domain.feed.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
