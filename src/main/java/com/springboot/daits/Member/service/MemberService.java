package com.springboot.daits.Member.service;

import com.springboot.daits.Member.dto.SignInResultDto;
import com.springboot.daits.Member.dto.SignUpResultDto;
import com.springboot.daits.Member.exception.UserNotFoundException;
import com.springboot.daits.Member.model.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

public interface MemberService extends UserDetailsService {

        UserDetails loadUserByUsername(String email) throws UserNotFoundException;

        MemberResponse getUser(String email);

        SignUpResultDto signUp(MemberInput memberInput);

        SignInResultDto signIn(MemberLoginInput memberLoginInput);

        ResponseEntity<?> updateUser(MemberUpdateInput memberUpdateInput);

        ResponseEntity<?> updateUserPassword(MemberInputPassword memberInputPassword, Errors errors);

        ResponseEntity<?> deleteUser(MemberInput memberInput);

}
