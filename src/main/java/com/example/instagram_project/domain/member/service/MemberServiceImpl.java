package com.example.instagram_project.domain.member.service;

import com.example.instagram_project.domain.feed.dto.PostDTO;
import com.example.instagram_project.domain.member.dto.request.MemberProfileEditRequest;
import com.example.instagram_project.domain.member.dto.response.MemberResponse;
import com.example.instagram_project.global.config.aws.S3Manager;
import com.example.instagram_project.global.jwt.JwtTokenProvider;
import com.example.instagram_project.domain.member.repository.MemberRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.domain.member.dto.request.MemberLoginRequest;
import com.example.instagram_project.domain.member.dto.request.MemberSignUpRequest;
import com.example.instagram_project.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final S3Manager s3Manager;
    private final AuthUtil authUtil;

    @Override
    public MemberResponse getProfile() {
        Member member = authUtil.getLoginMember();

        List<PostDTO> posts = member.getPosts().stream()
                .map(PostDTO::from)
                .toList();

        return MemberResponse.builder()
                .nickName(member.getNickname())
                .image(member.getProfileImage())
                .introduce(member.getIntroduce())
                .posts(posts)
                .build();
    }

    @Override
    @Transactional
    public Long signUp(MemberSignUpRequest requestDTO) throws Exception {
        if (memberRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (!requestDTO.getPassword().equals(requestDTO.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        Member member = memberRepository.save(requestDTO.toEntity());
        member.encodePassword(passwordEncoder);

        member.addUserAuthority();
        return member.getId();
    }

    @Override
    public String login(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = memberLoginRequest.getPassword();
        if (!member.checkPassword(passwordEncoder, password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getAuthority().name());

        return jwtTokenProvider.createToken(member.getEmail(), roles);
    }

    @Override
    @Transactional
    public void editProfile(MemberProfileEditRequest memberProfileEditRequest) {
        Member member = authUtil.getLoginMember();
        String profileImage = s3Manager.uploadFile(memberProfileEditRequest.getProfileImage());

        member.edit(memberProfileEditRequest.getNickName(), memberProfileEditRequest.getIntroduce(), profileImage);
    }

    @Override
    @Transactional
    public void delete() {
        Member member = authUtil.getLoginMember();
        memberRepository.delete(member);
    }
}
