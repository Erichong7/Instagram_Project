package com.example.instagram_project.domain.follow.controller;

import com.example.instagram_project.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followUserName}")
    public ResponseEntity<Void> follow(@PathVariable String followUserName) throws IllegalAccessException {
        followService.follow(followUserName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followUserName}")
    public ResponseEntity<Void> unfollow(@PathVariable String followUserName) throws IllegalAccessException {
        followService.unfollow(followUserName);
        return ResponseEntity.ok().build();
    }
}
