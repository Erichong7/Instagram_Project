package com.example.instagram_project.domain.member.service;

import com.example.instagram_project.domain.member.dto.MemberLoginRequest;
import com.example.instagram_project.domain.member.dto.MemberSignUpRequest;

public interface MemberService {
    public Long signUp(MemberSignUpRequest requestDTO) throws Exception;

    String login(MemberLoginRequest memberLoginRequest);

    void delete();
}
