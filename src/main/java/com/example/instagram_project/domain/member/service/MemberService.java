package com.example.instagram_project.domain.member.service;

import com.example.instagram_project.domain.member.dto.request.MemberLoginRequest;
import com.example.instagram_project.domain.member.dto.request.MemberProfileEditRequest;
import com.example.instagram_project.domain.member.dto.request.MemberSignUpRequest;
import com.example.instagram_project.domain.member.dto.response.MemberResponse;

public interface MemberService {
    Long signUp(MemberSignUpRequest requestDTO) throws Exception;

    String login(MemberLoginRequest memberLoginRequest);

    void delete();

    void editProfile(MemberProfileEditRequest memberProfileEditRequest);

    MemberResponse getProfile();
}
