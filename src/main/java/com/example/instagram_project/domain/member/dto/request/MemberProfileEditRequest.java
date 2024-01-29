package com.example.instagram_project.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
public class MemberProfileEditRequest {

    private String nickName;
    private String introduce;
    private MultipartFile profileImage;
}
