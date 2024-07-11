package com.springboot.daits.Member.controller;

import com.springboot.daits.Member.model.*;
import com.springboot.daits.Member.repository.MemberRepository;
import com.springboot.daits.Member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid MemberInput memberInput, Errors errors) {

        return memberService.signUp(memberInput, errors);
    }

    // 유저 정보 조회
    @GetMapping("")
    public MemberResponse getUser(@RequestBody String email) {

        return memberService.getUser(email);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid MemberLoginInput memberLoginInput) {

        return memberService.signIn(memberLoginInput);
    }

    // 로그아웃
    public void logout() {

    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid MemberUpdateInput memberUpdateInput) {

        return memberService.updateUser(memberUpdateInput);
    }

    // 비밀번호 수정
    @PatchMapping("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody @Valid MemberInputPassword memberInputPassword, Errors errors) {

        return memberService.updateUserPassword(memberInputPassword, errors);
    }

    // 회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody @Valid MemberInput memberInput) {

        return memberService.deleteUser(memberInput);
    }
}
