package com.example.instagram_project.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용을 입력하시오.")
    @Length(max = 100, message = "최대 100자까지 입력 가능합니다.")
    private String content;
}
