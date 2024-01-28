package com.example.instagram_project.domain.feed.dto;

import com.example.instagram_project.domain.feed.entity.PostImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostImageDTO {

    private Long id;
    private String image;

    @Builder
    public PostImageDTO(Long id, String image) {
        this.id = id;
        this.image = image;
    }

    public static PostImageDTO from(PostImage postImage) {
        return PostImageDTO.builder()
                .id(postImage.getId())
                .image(postImage.getImage())
                .build();
    }
}