package com.springboot.daits.User.controller;

import com.springboot.daits.User.entity.User;
import com.springboot.daits.User.exception.UserNotFoundException;
import com.springboot.daits.User.model.*;
import com.springboot.daits.User.repository.UserRepository;
import com.springboot.daits.User.service.UserService;
import com.springboot.daits.response.ResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserInput userInput, Errors errors) {

        return userService.signUp(userInput, errors);
    }

    // 유저 정보 조회
    @GetMapping("")
    public UserResponse getUser(@RequestBody String email) {

        return userService.getUser(email);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserLoginInput userLoginInput) {

        return userService.signIn(userLoginInput);
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateInput userUpdateInput) {

        return userService.updateUser(userUpdateInput);
    }

    // 비밀번호 수정
    @PatchMapping("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody @Valid UserInputPassword userInputPassword, Errors errors) {

        return userService.updateUserPassword(userInputPassword, errors);
    }

    // 회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody @Valid UserInput userInput) {

        return userService.deleteUser(userInput);
    }
}
