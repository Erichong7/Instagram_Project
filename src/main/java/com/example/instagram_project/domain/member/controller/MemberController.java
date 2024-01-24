package com.example.instagram_project.domain.member.controller;

import com.example.instagram_project.domain.member.dto.MemberLoginDTO;
import com.example.instagram_project.domain.member.dto.MemberSignUpRequestDTO;
import com.example.instagram_project.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@Valid @RequestBody MemberSignUpRequestDTO request) throws Exception {
        return ResponseEntity.ok(memberService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginDTO memberLoginDTO) {
        return ResponseEntity.ok(memberService.login(memberLoginDTO));
    }
}
