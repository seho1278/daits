package com.springboot.daits.Member.service.impl;

import com.springboot.daits.Member.entity.Member;
import com.springboot.daits.Member.exception.PasswordNotMatchException;
import com.springboot.daits.Member.exception.UserNotFoundException;
import com.springboot.daits.Member.model.*;
import com.springboot.daits.Member.repository.MemberRepository;
import com.springboot.daits.Member.service.MemberService;
import com.springboot.daits.Member.util.PasswordUtil;
import com.springboot.daits.response.ResponseError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 유저 여부 확인
    public Member checkUser(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 아닙니다."));

        return member;
    }

    // 유저 정보 조회
    public MemberResponse getUser(String email) {
        Member member = checkUser(email);

        MemberResponse memberResponse = MemberResponse.of(member);

        return memberResponse;
    }

    // 회원가입
    public ResponseEntity<?> signUp(MemberInput memberInput, Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        if (!memberInput.isPasswordConfirmValid()) {
            responseErrorList.add(new ResponseError("passwordConfirm", "비밀번호가 일치하지 않습니다."));
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        // password 암호화
        String encryptPassword = PasswordUtil.getEncryptPassword(memberInput.getPassword());

        Member member = Member.builder()
                .email(memberInput.getEmail())
                .userName(memberInput.getUserName())
                .password(encryptPassword)
                .createdAt(LocalDateTime.now())
                .build();

        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }

    // 로그인
    public ResponseEntity<?> signIn(MemberLoginInput memberLoginInput) {
        // 유저 정보가 있는지 확인
        Member member = checkUser(memberLoginInput.getEmail());

        // 입력한 비밀번호와 암호화된 비밀번호가 매칭되는지 확인
        if (!PasswordUtil.equalsPassword(memberLoginInput.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }



        return ResponseEntity.ok().body("로그인 성공");
    }

    // 회원정보 수정
    public ResponseEntity<?> updateUser(MemberUpdateInput memberUpdateInput) {
        Member member = checkUser(memberUpdateInput.getEmail());
        member.setUserName(memberUpdateInput.getUserName());

        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경
    public ResponseEntity<?> updateUserPassword(MemberInputPassword memberInputPassword, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        // 유저 정보가 있는지 확인
        Member member = checkUser(memberInputPassword.getEmail());

        // 입력한 비밀번호와 암호화된 비밀번호가 매칭되는지 확인
        if (!PasswordUtil.equalsPassword(memberInputPassword.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (!memberInputPassword.isNewPasswordConfirmValid()) {
            responseErrorList.add(new ResponseError("newPasswordConfirm", "비밀번호가 일치하지 않습니다."));
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        String encryptPassword = PasswordUtil.getEncryptPassword(memberInputPassword.getNewPassword());

        member.setPassword(encryptPassword);
        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }


    // 회원탈퇴
    public ResponseEntity<?> deleteUser(MemberInput memberInput) {
        Member member = checkUser(memberInput.getEmail());

        memberRepository.delete(member);

        return ResponseEntity.ok().body("회원탈퇴 처리가 정상적으로 처리되었습니다.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
