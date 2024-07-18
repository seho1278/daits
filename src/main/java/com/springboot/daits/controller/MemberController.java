package com.springboot.daits.controller;

import com.springboot.daits.model.*;
import com.springboot.daits.response.MemberResponse;
import com.springboot.daits.response.SignInResultDto;
import com.springboot.daits.response.SignUpResultDto;
import com.springboot.daits.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/signup")
    public SignUpResultDto signUp(@RequestBody @Valid MemberInput memberInput) {
        LOGGER.info("[signUp] 회원가입을 수행합니다. email : {}, password : ****, userName : {}, role : {}",
                memberInput.getEmail(), memberInput.getUserName(), memberInput.getRoles());
        SignUpResultDto signUpResultDto = memberService.signUp(memberInput);
        LOGGER.info("[signUp] 회원가입을 완료했습니다. email : {}", memberInput.getEmail());

        return signUpResultDto;
    }

    // 유저 정보 조회
    @GetMapping("")
    public MemberResponse getUser(@RequestBody String email) {

        return memberService.getUser(email);
    }

    // 로그인
    @PostMapping("/signin")
    public SignInResultDto signIn(@RequestBody @Valid MemberLoginInput memberLoginInput) {

        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", memberLoginInput.getEmail());
        SignInResultDto signInResultDto = memberService.signIn(memberLoginInput);

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}",
                    memberLoginInput.getEmail(), signInResultDto.getToken());
        }

        return signInResultDto;
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
