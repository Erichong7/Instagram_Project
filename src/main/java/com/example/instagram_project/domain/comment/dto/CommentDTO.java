package com.example.instagram_project.domain.comment.dto;

import com.example.instagram_project.domain.comment.entity.Comment;
import com.example.instagram_project.domain.member.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private CommentDTO parent;  // 부모 댓글의 DTO
    private MemberDTO member;
    private Long postId;
    private String content;
    private LocalDateTime uploadDate;

    @Builder
    public CommentDTO(Long id, CommentDTO parent, MemberDTO member, Long postId, String content, LocalDateTime uploadDate) {
        this.id = id;
        this.parent = parent;
        this.member = member;
        this.postId = postId;
        this.content = content;
        this.uploadDate = uploadDate;
    }

    public static CommentDTO from(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .parent(comment.getParent() != null ? from(comment.getParent()) : null)
                .member(MemberDTO.from(comment.getMember()))
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .uploadDate(comment.getUploadDate())
                .build();
    }
}