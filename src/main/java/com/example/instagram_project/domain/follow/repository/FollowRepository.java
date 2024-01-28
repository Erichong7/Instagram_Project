package com.example.instagram_project.domain.follow.repository;

import com.example.instagram_project.domain.follow.entity.Follow;
import com.example.instagram_project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByMemberIdAndFollowMemberId(Long id, Long id2);

    Optional<Follow> findByMemberIdAndFollowMemberId(Long id, Long id2);

    List<Follow> findAllByMember(Member member);
}
