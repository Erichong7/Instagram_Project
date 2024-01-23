package com.example.instagram_project.service;

import com.example.instagram_project.config.jwt.JwtTokenProvider;
import com.example.instagram_project.domain.Member;
import com.example.instagram_project.dto.MemberLoginDTO;
import com.example.instagram_project.dto.MemberSignUpRequestDTO;
import com.example.instagram_project.repository.MemberRepository;
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

    @Override
    @Transactional
    public Long signUp(MemberSignUpRequestDTO requestDTO) throws Exception {
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

    @Transactional
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).get();
    }

    @Override
    public String login(MemberLoginDTO memberLoginDTO) {

        Member member = memberRepository.findByEmail(memberLoginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = memberLoginDTO.getPassword();
        if (!member.checkPassword(passwordEncoder, password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getAuthority().name());

        return jwtTokenProvider.createToken(member.getNickname(), roles);
    }
}
