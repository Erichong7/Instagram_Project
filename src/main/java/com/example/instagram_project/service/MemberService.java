package com.example.instagram_project.service;

import com.example.instagram_project.dto.MemberSignUpRequestDTO;

public interface MemberService {
    public Long signUp(MemberSignUpRequestDTO requestDTO) throws Exception;
}
