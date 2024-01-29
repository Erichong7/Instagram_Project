package com.example.instagram_project.domain.member.entity;

import com.example.instagram_project.domain.feed.entity.Post;
import com.example.instagram_project.domain.follow.entity.Follow;
import com.example.instagram_project.global.auth.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String introduce;

    private String profileImage;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Follow> followings;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Post> posts;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void addUserAuthority() {
        this.authority = Authority.USER;
    }

    public boolean checkPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public void edit(String nickName, String introduce, String image) {
        this.nickname = (nickName != null) ? nickName : this.nickname;
        this.introduce = (introduce != null) ? introduce : this.introduce;
        this.profileImage = (image != null) ? image : this.profileImage;
    }
}
