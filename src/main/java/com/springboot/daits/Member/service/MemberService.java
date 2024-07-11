package com.springboot.daits.Member.service;

import com.springboot.daits.Member.model.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Errors;

public interface MemberService extends UserDetailsService {

        MemberResponse getUser(String email);

        ResponseEntity<?> signUp(MemberInput memberInput, Errors errors);

        ResponseEntity<?> signIn(MemberLoginInput memberLoginInput);

        ResponseEntity<?> updateUser(MemberUpdateInput memberUpdateInput);

        ResponseEntity<?> updateUserPassword(MemberInputPassword memberInputPassword, Errors errors);

        ResponseEntity<?> deleteUser(MemberInput memberInput);

}
