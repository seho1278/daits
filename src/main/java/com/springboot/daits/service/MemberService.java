package com.springboot.daits.service;

import com.springboot.daits.model.*;
import com.springboot.daits.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
