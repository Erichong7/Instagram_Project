package com.example.instagram_project.domain.feed.dto;

import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.member.dto.response.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private MemberDTO member;
    private String content;
    private LocalDateTime uploadDate;
    private List<PostImageDTO> postImages;

    @Builder
    public PostDTO(Long id, MemberDTO member, String content, LocalDateTime uploadDate, List<PostImageDTO> postImages) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.uploadDate = uploadDate;
        this.postImages = postImages;
    }

    public static PostDTO from(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .member(MemberDTO.from(post.getMember()))
                .content(post.getContent())
                .uploadDate(post.getUploadDate())
                .postImages(post.getPostImages().stream().map(PostImageDTO::from).collect(Collectors.toList()))
                .build();
    }
}

