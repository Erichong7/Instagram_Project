package com.example.instagram_project.domain.feed.repository;

import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMemberIn(List<Member> followedMembers);
}
