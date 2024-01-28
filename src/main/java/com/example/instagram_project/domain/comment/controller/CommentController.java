package com.example.instagram_project.domain.comment.controller;

import com.example.instagram_project.domain.comment.dto.request.CommentRequest;
import com.example.instagram_project.domain.comment.dto.request.CommentUpdateRequest;
import com.example.instagram_project.domain.comment.dto.request.CommentReplyRequest;
import com.example.instagram_project.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Void> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> createCommentReply(@Valid @RequestBody CommentReplyRequest commentReplyRequest) {
        commentService.createReply(commentReplyRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Void> update(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        commentService.update(commentId, commentUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
