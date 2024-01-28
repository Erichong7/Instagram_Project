package com.example.instagram_project.domain.comment.repository;

import com.example.instagram_project.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
