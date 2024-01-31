package com.example.instagram_project.post;

import com.example.instagram_project.domain.comment.dto.request.CommentRequest;
import com.example.instagram_project.domain.comment.dto.request.CommentUpdateRequest;
import com.example.instagram_project.domain.comment.entity.Comment;
import com.example.instagram_project.domain.comment.repository.CommentRepository;
import com.example.instagram_project.domain.comment.service.CommentService;
import com.example.instagram_project.domain.feed.dto.response.PostResponse;
import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.feed.repository.PostRepository;
import com.example.instagram_project.domain.feed.service.PostService;
import com.example.instagram_project.domain.follow.entity.Follow;
import com.example.instagram_project.domain.follow.repository.FollowRepository;
import com.example.instagram_project.domain.member.dto.request.MemberSignUpRequest;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.domain.member.repository.MemberRepository;
import com.example.instagram_project.domain.member.service.MemberService;
import com.example.instagram_project.global.auth.Authority;
import com.example.instagram_project.global.config.aws.S3Manager;
import com.example.instagram_project.global.jwt.JwtTokenProvider;
import com.example.instagram_project.global.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Manager s3Manager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthUtil authUtil;

    @InjectMocks
    private PostService postService;

    @InjectMocks
    private CommentService commentService;

    @InjectMocks
    private MemberService memberService;

    // 테스트를 위한 가상의 데이터들
    private static final Long POST_ID = 1L;
    private static final Long COMMENT_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPosts() {
        // 가상의 사용자와 게시물 데이터
        Member member = buildMember();
        Post post = buildPost(member);

        // Mock 객체 설정
        when(authUtil.getLoginMember()).thenReturn(member);
        when(followRepository.findAllByMember(any())).thenReturn(Arrays.asList(buildFollow(member)));
        when(postRepository.findAllByMemberIn(any())).thenReturn(Arrays.asList(post));

        // 테스트 수행
        PostResponse postResponse = postService.getPosts();

        // 결과 검증
        assertNotNull(postResponse);
        assertEquals(1, postResponse.getPosts().size());
        verify(authUtil, times(1)).getLoginMember();
        verify(followRepository, times(1)).findAllByMember(any());
        verify(postRepository, times(1)).findAllByMemberIn(any());
    }

    @Test
    void createComment() {
        // 가상의 사용자와 게시물 데이터
        Member member = buildMember();
        Post post = buildPost(member);
        CommentRequest commentRequest = buildCommentRequest();

        // Mock 객체 설정
        when(authUtil.getLoginMember()).thenReturn(member);
        when(postRepository.findById(any())).thenReturn(java.util.Optional.of(post));

        // 테스트 수행
        commentService.createComment(commentRequest);

        // 결과 검증
        verify(authUtil, times(1)).getLoginMember();
        verify(postRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void updateComment() {
        // 가상의 사용자와 댓글 데이터
        Member member = buildMember();
        CommentUpdateRequest commentUpdateRequest = buildCommentUpdateRequest();

        // Mock 객체 설정
        when(authUtil.getLoginMember()).thenReturn(member);
        when(commentRepository.findById(any())).thenReturn(java.util.Optional.of(Comment.builder().build()));

        // 테스트 수행
        commentService.update(COMMENT_ID, commentUpdateRequest);

        // 결과 검증
        verify(authUtil, times(1)).getLoginMember();
        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void deleteComment() {
        // 가상의 사용자와 댓글 데이터
        Member member = buildMember();

        // Mock 객체 설정
        when(authUtil.getLoginMember()).thenReturn(member);
        when(commentRepository.findById(any())).thenReturn(java.util.Optional.of(Comment.builder().build()));

        // 테스트 수행
        commentService.delete(COMMENT_ID);

        // 결과 검증
        verify(authUtil, times(1)).getLoginMember();
        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    void signUp() {
        // 가상의 가입 요청 데이터
        MemberSignUpRequest signUpRequest = buildSignUpRequest();

        // 테스트 수행
        memberService.signUp(signUpRequest);

        // 결과 검증
        verify(memberRepository, times(1)).findByEmail(any());
        verify(memberRepository, times(1)).save(any());
    }

    // 나머지 테스트 케이스에 대한 메소드도 유사하게 작성할 수 있습니다.

    // 가상의 Member 객체 생성
    private Member buildMember() {
        return Member.builder()
                .id(1L)
                .email("test@example.com")
                .nickname("testuser")
                .password("password")
                .authority(Authority.USER)
                .build();
    }

    // 가상의 Post 객체 생성
    private Post buildPost(Member member) {
        return Post.builder()
                .member(member)
                .content("Test post content")
                .build();
    }

    // 가상의 CommentRequest 객체 생성
    private CommentRequest buildCommentRequest() {
        return CommentRequest.builder()
                .postId(POST_ID)
                .content("Test comment content")
                .build();
    }

    // 가상의 CommentUpdateRequest 객체 생성
    private CommentUpdateRequest buildCommentUpdateRequest() {
        return CommentUpdateRequest.builder()
                .content("Updated comment content")
                .build();
    }

    // 가상의 Follow 객체 생성
    private Follow buildFollow(Member member) {
        return Follow.builder()
                .member(member)
                .followMember(buildMember())
                .build();
    }

    // 가상의 SignUpRequest 객체 생성
    private MemberSignUpRequest buildSignUpRequest() {
        return MemberSignUpRequest.builder()
                .email("newuser@example.com")
                .nickname("newuser")
                .password("newpassword")
                .checkedPassword("newpassword")
                .authority(Authority.USER)
                .build();
    }
}

