package com.example.instagram_project.domain.feed.controller;

import com.example.instagram_project.domain.feed.dto.request.PostRequest;
import com.example.instagram_project.domain.feed.dto.response.PostResponse;
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

    @GetMapping
    public ResponseEntity<PostResponse> getPosts() {
        PostResponse postResponse = postService.getPosts();
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping
    public ResponseEntity<Void> uploadPost(@Valid PostRequest postRequest) {
        postService.upload(postRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @Valid PostRequest postRequest) throws IllegalAccessException {
        postService.update(postId, postRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) throws IllegalAccessException {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }
}
