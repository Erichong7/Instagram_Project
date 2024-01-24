package com.example.instagram_project.domain.feed.entity;

import com.example.instagram_project.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //    @OneToMany(mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "post")
//    private List<PostImage> postImages = new ArrayList<>();
}
