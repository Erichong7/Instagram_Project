package com.example.instagram_project.domain.feed.controller;

import com.example.instagram_project.domain.feed.dto.PostDTO;
import com.example.instagram_project.domain.feed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> uploadPost(@Valid PostDTO postDTO) {
        postService.upload(postDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @Valid PostDTO postDTO) throws IllegalAccessException {
        postService.update(postId, postDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) throws IllegalAccessException {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }
}
