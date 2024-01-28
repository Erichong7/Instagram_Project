package com.example.instagram_project.domain.follow.repository;

import com.example.instagram_project.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByMemberIdAndFollowMemberId(Long id, Long id1);

    Optional<Follow> findByMemberIdAndFollowMemberId(Long id, Long id1);
}
