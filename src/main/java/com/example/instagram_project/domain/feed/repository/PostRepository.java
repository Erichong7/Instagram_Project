package com.example.instagram_project.domain.feed.repository;

import com.example.instagram_project.domain.feed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
