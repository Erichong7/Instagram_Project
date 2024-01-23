package com.example.instagram_project.service;

import com.example.instagram_project.dto.MemberLoginDTO;
import com.example.instagram_project.dto.MemberSignUpRequestDTO;

public interface MemberService {
    public Long signUp(MemberSignUpRequestDTO requestDTO) throws Exception;

    String login(MemberLoginDTO memberLoginDTO);
}
