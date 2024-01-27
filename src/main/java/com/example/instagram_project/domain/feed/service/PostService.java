package com.example.instagram_project.domain.feed.service;

import com.example.instagram_project.domain.feed.dto.PostUploadRequestDTO;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.repository.PostRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostImageService postImageService;
    private final SecurityUtil securityUtil;

    @Transactional
    public void upload(PostUploadRequestDTO postUploadRequestDTO) {
        Member member = securityUtil.getLoginMember();

        Post post = Post.builder()
                .member(member)
                .content(postUploadRequestDTO.getContent())
                .build();

        postImageService.saveAll(post, postUploadRequestDTO.getPostImages());
        postRepository.save(post);
    }
}
