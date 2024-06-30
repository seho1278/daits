package com.springboot.daits.User.service;

import com.springboot.daits.User.entity.User;
import com.springboot.daits.User.exception.UserNotFoundException;
import com.springboot.daits.User.model.UserInput;
import com.springboot.daits.User.model.UserLoginInput;
import com.springboot.daits.User.model.UserResponse;
import com.springboot.daits.User.model.UserUpdateInput;
import com.springboot.daits.User.repository.UserRepository;
import com.springboot.daits.response.ResponseError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 여부 확인
    public User checkUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 아닙니다."));

        return user;
    }

    // 유저 정보 조회
    public UserResponse getUser(String email) {
        User user = checkUser(email);

        UserResponse userResponse = UserResponse.of(user);

        return userResponse;
    }

    // 회원가입
    public ResponseEntity<?> signUp(UserInput userInput, Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(userInput.getPassword())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    // 로그인
    public ResponseEntity<?> signIn(UserLoginInput userLoginInput) {
        User user = checkUser(userLoginInput.getEmail());

        return ResponseEntity.ok().body("로그인 성공");
    }

    // 회원정보 수정
    public ResponseEntity<?> updateUser(UserUpdateInput userUpdateInput) {
        User user = checkUser(userUpdateInput.getEmail());
        user.setUserName(userUpdateInput.getUserName());

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    // 회원탈퇴
    public ResponseEntity<?> deleteUser(UserInput userInput) {
        User user = checkUser(userInput.getEmail());

        userRepository.delete(user);

        return ResponseEntity.ok().body("회원탈퇴 처리가 정상적으로 처리되었습니다.");
    }
}
