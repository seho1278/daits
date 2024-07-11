package com.springboot.daits.Member.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberUpdateInput {
    
    private String email;
    
    @NotBlank(message = "변경할 닉네임을 입력해주세요")
    private String userName;

}
