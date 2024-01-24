package com.example.instagram_project.domain.feed.controller;

import com.example.instagram_project.domain.feed.dto.PostUploadRequestDTO;
import com.example.instagram_project.domain.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> uploadPost(@Valid @RequestBody PostUploadRequestDTO postUploadRequestDTO) {
        postService.upload(postUploadRequestDTO);
        return ResponseEntity.ok().build();
    }
}
