package com.example.instagram_project.domain.feed.service;

import com.example.instagram_project.domain.feed.dto.PostDTO;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.repository.PostRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostImageService postImageService;
    private final AuthUtil authUtil;

    @Transactional
    public void upload(PostDTO postDTO) {
        Member member = authUtil.getLoginMember();

        Post post = Post.builder()
                .member(member)
                .content(postDTO.getContent())
                .build();

        postImageService.saveAll(post, postDTO.getPostImages());
        postRepository.save(post);
    }

    @Transactional
    public void update(Long postId, PostDTO postDTO) throws IllegalAccessException {
        Post post = validate(postId);
        post.update(postDTO.getContent());
        postImageService.updateAll(post, postDTO.getPostImages());
    }

    @Transactional
    public void delete(Long postId) throws IllegalAccessException {
        Post post = validate(postId);
        postRepository.deleteById(postId);
        postImageService.deleteAllByPost(post);
    }

    private Post validate(Long postId) throws IllegalAccessException {
        Member member = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        if (!post.getMember().equals(member)) {
            throw new IllegalAccessException("게시물을 수정할 권한이 없습니다.");
        }

        return post;
    }
}
