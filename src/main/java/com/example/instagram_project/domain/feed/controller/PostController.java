package com.example.instagram_project.domain.feed.controller;

import com.example.instagram_project.domain.feed.dto.PostUploadRequestDTO;
import com.example.instagram_project.domain.feed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> uploadPost(@Valid PostUploadRequestDTO postUploadRequestDTO) {
        postService.upload(postUploadRequestDTO);
        return ResponseEntity.ok().build();
    }

}
