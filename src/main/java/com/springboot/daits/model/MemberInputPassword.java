package com.springboot.daits.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInputPassword {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "현재 비밀번호를 입력해 주세요")
    private String password;

    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 작성해 주세요")
    @NotBlank(message = "새로운 비밀번호를 입력해 주세요")
    private String newPassword;

    @NotBlank(message = "비밀번호 확인을 위해 새로운 비밀번호를 한번 더 입력해 주세요")
    private String newPasswordConfirm;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isNewPasswordConfirmValid() {
        return newPassword.equals(newPasswordConfirm);
    }
}
