package com.example.instagram_project.domain.member.controller;

import com.example.instagram_project.domain.member.dto.request.MemberLoginRequest;
import com.example.instagram_project.domain.member.dto.request.MemberProfileEditRequest;
import com.example.instagram_project.domain.member.dto.request.MemberSignUpRequest;
import com.example.instagram_project.domain.member.dto.response.MemberResponse;
import com.example.instagram_project.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> getProfile() {
        return ResponseEntity.ok(memberService.getProfile());
    }

    @PostMapping("/join")
    public ResponseEntity<Long> join(@Valid @RequestBody MemberSignUpRequest request) {
        return ResponseEntity.ok(memberService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PutMapping
    public ResponseEntity<Void> editProfile(MemberProfileEditRequest memberProfileEditRequest) {
        memberService.editProfile(memberProfileEditRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete() {
        memberService.delete();
        return ResponseEntity.ok().build();
    }
}
