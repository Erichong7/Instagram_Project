package com.example.instagram_project.global.util;

import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final MemberRepository memberRepository;

    public Member getLoginMember() {
        try {
            String loginMember = SecurityContextHolder.getContext().getAuthentication().getName();
            return memberRepository.findByEmail(loginMember)
                    .orElseThrow(() -> new RuntimeException("ID에 해당하는 회원을 찾을 수 없습니다. ID: " + loginMember));
        } catch (Exception e) {
            throw new RuntimeException("로그인 멤버를 가져오는 중 오류가 발생했습니다.", e);
        }
    }

}
