package com.example.instagram_project.domain.comment.service;

import com.example.instagram_project.domain.comment.dto.CommentRequest;
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
    public void create(CommentRequest commentRequest) {
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
}
