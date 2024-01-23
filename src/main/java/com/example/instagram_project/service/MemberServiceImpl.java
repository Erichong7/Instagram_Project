package com.example.instagram_project.service;

import com.example.instagram_project.domain.Member;
import com.example.instagram_project.dto.MemberSignUpRequestDTO;
import com.example.instagram_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
}
