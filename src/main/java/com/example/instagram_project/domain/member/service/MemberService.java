package com.example.instagram_project.domain.member.service;

import com.example.instagram_project.domain.member.dto.MemberLoginDTO;
import com.example.instagram_project.domain.member.dto.MemberSignUpRequestDTO;

public interface MemberService {
    public Long signUp(MemberSignUpRequestDTO requestDTO) throws Exception;

    String login(MemberLoginDTO memberLoginDTO);
}
