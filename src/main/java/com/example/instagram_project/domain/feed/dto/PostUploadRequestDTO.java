package com.example.instagram_project.domain.feed.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PostUploadRequestDTO {
    @Size(max = 2200, message = "게시물 내용은 최대 2,200자까지 입력 가능합니다.")
    private String content;
}
