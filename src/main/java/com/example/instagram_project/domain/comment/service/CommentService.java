package com.example.instagram_project.domain.comment.service;

import com.example.instagram_project.domain.comment.dto.request.CommentReplyRequest;
import com.example.instagram_project.domain.comment.dto.request.CommentRequest;
import com.example.instagram_project.domain.comment.dto.request.CommentUpdateRequest;
import com.example.instagram_project.domain.comment.entity.Comment;
import com.example.instagram_project.domain.comment.repository.CommentRepository;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.repository.PostRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.global.util.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthUtil authUtil;

    @Transactional
    public void createComment(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
        Member member = authUtil.getLoginMember();
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(commentRequest.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void createReply(CommentReplyRequest commentReplyRequest) {
        Post post = postRepository.findById(commentReplyRequest.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
        Member member = authUtil.getLoginMember();
        Comment parentComment = commentRepository.findById(commentReplyRequest.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("부모 댓글을 찾을 수 없습니다."));
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .parent(parentComment)
                .content(commentReplyRequest.getContent())
                .build();

        parentComment.getChildren().add(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(Long commentId, CommentUpdateRequest commentUpdateRequest) throws IllegalAccessException {
        Comment comment = validate(commentId);
        comment.update(commentUpdateRequest.getContent());
    }

    @Transactional
    public void delete(Long commentId) throws IllegalAccessException {
        Comment comment = validate(commentId);
        commentRepository.delete(comment);
    }

    private Comment validate(Long commentId) throws IllegalAccessException {
        Member member = authUtil.getLoginMember();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if(!comment.getMember().equals(member)) {
            throw new IllegalAccessException("게시물을 수정할 권한이 없습니다.");
        }

        return comment;
    }
}
