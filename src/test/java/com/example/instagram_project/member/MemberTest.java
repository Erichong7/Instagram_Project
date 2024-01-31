package com.example.instagram_project.member;

import com.example.instagram_project.domain.member.dto.request.MemberLoginRequest;
import com.example.instagram_project.domain.member.dto.request.MemberSignUpRequest;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.domain.member.repository.MemberRepository;
import com.example.instagram_project.domain.member.service.MemberServiceImpl;
import com.example.instagram_project.global.auth.Authority;
import com.example.instagram_project.global.config.aws.S3Manager;
import com.example.instagram_project.global.error.exception.EmailAlreadyExistException;
import com.example.instagram_project.global.error.exception.PasswordUncheckedException;
import com.example.instagram_project.global.error.exception.WrongPasswordException;
import com.example.instagram_project.global.jwt.JwtTokenProvider;
import com.example.instagram_project.global.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Manager s3Manager;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void signUp_Success() throws Exception {
        MemberSignUpRequest requestDTO = buildSignUpRequest();
        when(memberRepository.findByEmail(any())).thenReturn(java.util.Optional.empty());

        memberService.signUp(requestDTO);

        verify(memberRepository, times(1)).save(any());
        verify(memberRepository, times(1)).findByEmail(any());
    }

    @Test
    void signUp_EmailAlreadyExists_ExceptionThrown() {
        MemberSignUpRequest requestDTO = buildSignUpRequest();
        when(memberRepository.findByEmail(any())).thenReturn(java.util.Optional.of(Member.builder().build()));

        assertThrows(EmailAlreadyExistException.class, () -> memberService.signUp(requestDTO));

        verify(memberRepository, times(0)).save(any());
        verify(memberRepository, times(1)).findByEmail(any());
    }

    @Test
    void signUp_PasswordMismatch_ExceptionThrown() {
        MemberSignUpRequest requestDTO = buildSignUpRequest();
        requestDTO.setCheckedPassword("wrongPassword");

        assertThrows(PasswordUncheckedException.class, () -> memberService.signUp(requestDTO));

        verify(memberRepository, times(0)).save(any());
        verify(memberRepository, times(0)).findByEmail(any());
    }

    @Test
    void login_Success() {
        MemberLoginRequest loginRequest = buildLoginRequest();
        Member member = buildMember();
        when(memberRepository.findByEmail(any())).thenReturn(java.util.Optional.of(member));
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("dummyToken");

        String token = memberService.login(loginRequest);

        assertNotNull(token);
        verify(memberRepository, times(1)).findByEmail(any());
        verify(jwtTokenProvider, times(1)).createToken(any(), any());
    }

    @Test
    void login_InvalidPassword_ExceptionThrown() {
        MemberLoginRequest loginRequest = buildLoginRequest();
        Member member = buildMember();
        when(memberRepository.findByEmail(any())).thenReturn(java.util.Optional.of(member));
        when(jwtTokenProvider.createToken(any(), any())).thenReturn("dummyToken");

        loginRequest.setPassword("wrongPassword");

        assertThrows(WrongPasswordException.class, () -> memberService.login(loginRequest));

        verify(memberRepository, times(1)).findByEmail(any());
        verify(jwtTokenProvider, times(0)).createToken(any(), any());
    }

    // Similar tests can be written for other methods such as editProfile, delete, etc.

    private MemberSignUpRequest buildSignUpRequest() {
        return MemberSignUpRequest.builder()
                .email("te@example.com")
                .nickname("testUser")
                .password("Test@1234")
                .checkedPassword("Test@1234")
                .authority(Authority.USER)
                .build();
    }

    private MemberLoginRequest buildLoginRequest() {
        return MemberLoginRequest.builder()
                .email("te@example.com")
                .password("Test@1234")
                .build();
    }

    private Member buildMember() {
        return Member.builder()
                .id(1L)
                .email("te@example.com")
                .nickname("testUser")
                .password("encodedPassword")
                .introduce("Hello, I am a test user.")
                .profileImage("profileImage.jpg")
                .authority(Authority.USER)
                .build();
    }
}

