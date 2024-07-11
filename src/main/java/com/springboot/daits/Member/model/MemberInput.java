package com.springboot.daits.Member.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInput {

    @Email(message = "이메일 형식에 맞게 작성해 주세요")
    @NotBlank(message = "이메일을 입력해 주세요")
    private String email;

    @NotBlank(message = "닉네임을 입력해 주세요")
    private String userName;

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 작성해 주세요")
    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 위해 비밀번호를 입력해 주세요")
    private String passwordConfirm;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordConfirmValid() {
        return password.equals(passwordConfirm);
    }
}
