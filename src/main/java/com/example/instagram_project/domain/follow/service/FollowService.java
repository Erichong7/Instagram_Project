package com.example.instagram_project.domain.follow.service;

import com.example.instagram_project.domain.follow.entity.Follow;
import com.example.instagram_project.domain.follow.repository.FollowRepository;
import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.domain.member.repository.MemberRepository;
import com.example.instagram_project.global.error.Error;
import com.example.instagram_project.global.error.exception.FollowNestingException;
import com.example.instagram_project.global.error.exception.NoAuthException;
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
    public void follow(String followMemberName) {
        Member member = authUtil.getLoginMember();
        Member followMember = memberRepository.findByNickname(followMemberName)
                .orElseThrow(() -> new EntityNotFoundException(Error.NO_AUTH_MEMBER.getMessage()));

        if (member.getId().equals(followMember.getId())) {
            throw new NoAuthException();
        }

        if (followRepository.existsByMemberIdAndFollowMemberId(member.getId(), followMember.getId())) {
            throw new FollowNestingException();
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
                .orElseThrow(() -> new EntityNotFoundException(Error.NO_AUTH_MEMBER.getMessage()));

        if (member.getId().equals(followMember.getId())) {
            throw new IllegalAccessException();
        }

        Follow follow = followRepository
                .findByMemberIdAndFollowMemberId(member.getId(), followMember.getId())
                .orElseThrow(IllegalAccessException::new);
        followRepository.delete(follow);
    }
}
