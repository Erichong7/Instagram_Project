package com.example.instagram_project.domain.feed.service;

import com.example.instagram_project.domain.feed.dto.PostDTO;
import com.example.instagram_project.domain.feed.dto.request.PostRequest;
import com.example.instagram_project.domain.feed.dto.response.PostResponse;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.repository.PostRepository;
import com.example.instagram_project.domain.follow.entity.Follow;
import com.example.instagram_project.domain.follow.repository.FollowRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.global.error.Error;
import com.example.instagram_project.global.error.exception.NoAuthException;
import com.example.instagram_project.global.util.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final PostImageService postImageService;
    private final AuthUtil authUtil;

    public PostResponse getPosts() {
        Member member = authUtil.getLoginMember();
        List<Follow> followMembers = followRepository.findAllByMember(member);

        List<Member> followedMembers = followMembers.stream()
                .map(Follow::getFollowMember)
                .collect(Collectors.toList());

        List<Post> posts = postRepository.findAllByMemberIn(followedMembers);

        List<PostDTO> postDTOList = posts.stream()
                .map(PostDTO::from)
                .toList();

        return PostResponse.builder()
                .posts(postDTOList)
                .build();
    }

    public PostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(Error.NO_AUTH_MEMBER.getMessage()));

        return PostDTO.from(post);
    }

    @Transactional
    public void upload(PostRequest postRequest) {
        Member member = authUtil.getLoginMember();

        Post post = Post.builder()
                .member(member)
                .content(postRequest.getContent())
                .build();

        member.getPosts().add(post);
        postImageService.saveAll(post, postRequest.getPostImages());
        postRepository.save(post);
    }

    @Transactional
    public void update(Long postId, PostRequest postRequest) {
        Post post = validate(postId);
        post.update(postRequest.getContent());
        postImageService.updateAll(post, postRequest.getPostImages());
    }

    @Transactional
    public void delete(Long postId) {
        Post post = validate(postId);
        postRepository.deleteById(postId);
        postImageService.deleteAllByPost(post);
    }

    private Post validate(Long postId) {
        Member member = authUtil.getLoginMember();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(Error.NO_AUTH_MEMBER.getMessage()));

        if (!post.getMember().equals(member)) {
            throw new NoAuthException();
        }

        return post;
    }
}
