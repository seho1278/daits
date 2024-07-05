package com.springboot.daits.User.service;

import com.springboot.daits.User.entity.User;
import com.springboot.daits.User.exception.PasswordNotMatchException;
import com.springboot.daits.User.exception.UserNotFoundException;
import com.springboot.daits.User.model.*;
import com.springboot.daits.User.repository.UserRepository;
import com.springboot.daits.User.util.PasswordUtil;
import com.springboot.daits.response.ResponseError;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

        if (!userInput.isPasswordConfirmValid()) {
            responseErrorList.add(new ResponseError("passwordConfirm", "비밀번호가 일치하지 않습니다."));
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }
        
        // password 암호화
        String encryptPassword = PasswordUtil.getEncryptPassword(userInput.getPassword());

        User user = User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(encryptPassword)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    // 로그인
    public ResponseEntity<?> signIn(UserLoginInput userLoginInput) {
        // 유저 정보가 있는지 확인
        User user = checkUser(userLoginInput.getEmail());

        // 입력한 비밀번호와 암호화된 비밀번호가 매칭되는지 확인
        if (!PasswordUtil.equalsPassword(userLoginInput.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok().body("로그인 성공");
    }

    // 회원정보 수정
    public ResponseEntity<?> updateUser(UserUpdateInput userUpdateInput) {
        User user = checkUser(userUpdateInput.getEmail());
        user.setUserName(userUpdateInput.getUserName());

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
    
    // 비밀번호 변경
    public ResponseEntity<?> updateUserPassword(UserInputPassword userInputPassword, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        // 유저 정보가 있는지 확인
        User user = checkUser(userInputPassword.getEmail());

        // 입력한 비밀번호와 암호화된 비밀번호가 매칭되는지 확인
        if (!PasswordUtil.equalsPassword(userInputPassword.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (!userInputPassword.isNewPasswordConfirmValid()) {
            responseErrorList.add(new ResponseError("newPasswordConfirm", "비밀번호가 일치하지 않습니다."));
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }

        String encryptPassword = PasswordUtil.getEncryptPassword(userInputPassword.getNewPassword());

        user.setPassword(encryptPassword);
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
