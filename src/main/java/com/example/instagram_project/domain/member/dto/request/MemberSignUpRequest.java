package com.example.instagram_project.domain.member.dto.request;

import com.example.instagram_project.domain.member.entity.Member;
import com.example.instagram_project.global.auth.Authority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberSignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    private String checkedPassword;

    private Authority authority;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .introduce("")
                .profileImage("https://github.com/Erichong7/Instagram_Project/assets/97429550/05958834-4e72-4e5c-9cad-652b5c6071cc")
                .authority(Authority.USER)
                .build();
    }
}
