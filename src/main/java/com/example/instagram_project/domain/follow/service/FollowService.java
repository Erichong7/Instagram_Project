package com.example.instagram_project.domain.follow.service;

import com.example.instagram_project.domain.follow.entity.Follow;
import com.example.instagram_project.domain.follow.repository.FollowRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.domain.member.repository.MemberRepository;
import com.example.instagram_project.global.util.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;

    @Transactional
    public void follow(String followMemberName) throws IllegalAccessException {
        Member member = authUtil.getLoginMember();
        Member followMember = memberRepository.findByNickname(followMemberName)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        if (member.getId().equals(followMember.getId())) {
            throw new IllegalAccessException();
        }
        if (followRepository.existsByMemberIdAndFollowMemberId(member.getId(), followMember.getId())) {
            throw new IllegalAccessException();
        }

        Follow follow = Follow.builder()
                .member(member)
                .followMember(followMember)
                .build();
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(String followMemberName) throws IllegalAccessException {
        Member member = authUtil.getLoginMember();
        Member followMember = memberRepository.findByNickname(followMemberName)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        if (member.getId().equals(followMember.getId())) {
            throw new IllegalAccessException();
        }

        Follow follow = followRepository
                .findByMemberIdAndFollowMemberId(member.getId(), followMember.getId())
                .orElseThrow(IllegalAccessException::new);
        followRepository.delete(follow);
    }
}
