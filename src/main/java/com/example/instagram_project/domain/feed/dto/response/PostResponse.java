package com.example.instagram_project.domain.feed.dto.response;

import com.example.instagram_project.domain.feed.dto.PostDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {
    List<PostDTO> posts;

    @Builder
    public PostResponse(List<PostDTO> posts) {
        this.posts = posts;
    }
}
