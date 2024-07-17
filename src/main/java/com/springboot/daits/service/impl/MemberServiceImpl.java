package com.springboot.daits.service.impl;

import com.springboot.daits.model.*;
import com.springboot.daits.entity.Member;
import com.springboot.daits.exception.PasswordNotMatchException;
import com.springboot.daits.exception.UserNotFoundException;
import com.springboot.daits.repository.MemberRepository;
import com.springboot.daits.service.MemberService;
import com.springboot.daits.util.PasswordUtil;
import com.springboot.daits.response.ResponseError;
import com.springboot.daits.security.JwtTokenProvider;
import com.springboot.daits.security.common.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberServiceImpl(MemberRepository memberRepository, @Lazy JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 유저 여부 확인
    public Member checkUser(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 아닙니다."));

        return member;
    }

    // 유저 정보 조회
    @Override
    public MemberResponse getUser(String email) {
        Member member = checkUser(email);

        MemberResponse memberResponse = MemberResponse.of(member);

        return memberResponse;
    }

    // 회원가입
    @Override
    public SignUpResultDto signUp(MemberInput memberInput) {

        if (!memberInput.isPasswordConfirmValid()) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");

        Member member;

        // password 암호화
        String encryptPassword = PasswordUtil.getEncryptPassword(memberInput.getPassword());

        if (memberInput.getRoles().equals("admin")) {
            member = Member.builder()
                    .email(memberInput.getEmail())
                    .userName(memberInput.getUserName())
                    .password(encryptPassword)
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .createdAt(LocalDateTime.now())
                    .build();
        } else {
            member = Member.builder()
                    .email(memberInput.getEmail())
                    .userName(memberInput.getUserName())
                    .password(encryptPassword)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        Member savedMember = memberRepository.save(member);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        LOGGER.info("[getSignUpResult] memberEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedMember.getEmail().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }

        return signUpResultDto;
    }

    // 로그인
    @Override
    public SignInResultDto signIn(MemberLoginInput memberLoginInput) {
        // 유저 정보가 있는지 확인
//        Member member = checkUser(memberLoginInput.getEmail());
        LOGGER.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        Member member = memberRepository.getByEmail(memberLoginInput.getEmail());
        LOGGER.info("[getSignInResult] email : {}", memberLoginInput.getEmail());

        // 입력한 비밀번호와 암호화된 비밀번호가 매칭되는지 확인
        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!PasswordUtil.equalsPassword(memberLoginInput.getPassword(), member.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");


        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(member.getEmail()), member.getRoles()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    // 회원정보 수정
    @Override
    public ResponseEntity<?> updateUser(MemberUpdateInput memberUpdateInput) {
        Member member = checkUser(memberUpdateInput.getEmail());
        member.setUserName(memberUpdateInput.getUserName());

        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경
    @Override
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
    @Override
    public ResponseEntity<?> deleteUser(MemberInput memberInput) {
        Member member = checkUser(memberInput.getEmail());

        memberRepository.delete(member);

        return ResponseEntity.ok().body("회원탈퇴 처리가 정상적으로 처리되었습니다.");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. email : {}", email);
        return memberRepository.getByEmail(email);
    }
}
