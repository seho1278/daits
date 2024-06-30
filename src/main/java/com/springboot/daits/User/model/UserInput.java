package com.springboot.daits.User.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInput {

    @Email(message = "이메일 형식에 맞게 작성해 주세요")
    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    private String email;

    @NotBlank(message = "닉네임은은 필수 입력사항입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력사항입니다.")
    private String passwordConfirm;
}
