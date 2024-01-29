package com.example.instagram_project.domain.member.dto.response;

import com.example.instagram_project.domain.feed.dto.PostDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponse {
    private String nickName;
    private String image;
    private String introduce;
    private List<PostDTO> posts;
}
