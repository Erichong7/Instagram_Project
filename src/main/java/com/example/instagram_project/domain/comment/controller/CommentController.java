package com.example.instagram_project.domain.comment.controller;

import com.example.instagram_project.domain.comment.dto.CommentRequest;
import com.example.instagram_project.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        commentService.create(commentRequest);
        return ResponseEntity.ok().build();
    }
}
