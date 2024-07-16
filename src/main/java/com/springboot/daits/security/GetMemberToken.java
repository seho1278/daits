package com.springboot.daits.security;

import com.springboot.daits.Member.entity.Member;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetMemberToken {

    public Member getMemberToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //로그인이 되지 않은 경우
        if (authentication != null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        Member member = (Member) authentication.getPrincipal();

        return member;
    }
}
