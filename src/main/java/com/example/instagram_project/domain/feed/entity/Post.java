package com.example.instagram_project.domain.feed.entity;

import com.example.instagram_project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @CreatedDate
    private LocalDateTime uploadDate;

    @Builder
    public Post(Member member, String content) {
        this.member = member;
        this.content = content;
    }

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();

    public void update(String content) {
        this.content = content;
    }

    //    @OneToMany(mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();
}
