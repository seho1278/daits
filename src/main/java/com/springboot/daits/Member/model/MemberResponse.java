package com.springboot.daits.Member.model;

import com.springboot.daits.Member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {

    private String email;
    private String userName;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .userName(member.getUsername())
                .build();
    }

}
