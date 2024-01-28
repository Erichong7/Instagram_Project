package com.example.instagram_project.domain.feed.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostRequest {
    @Size(max = 2200, message = "게시물 내용은 최대 2,200자까지 입력 가능합니다.")
    private String content;

//    @Size(min = 1, max = 10, message = "게시물 이미지는 1개 이상, 10개 이하만 추가할 수 있습니다.")
    private List<MultipartFile> postImages = new ArrayList<>();
}
