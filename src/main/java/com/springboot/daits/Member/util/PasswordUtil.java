package com.springboot.daits.Member.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class PasswordUtil {

    // password를 암호화
    public static String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.encode(password);
    }

    // 입력한 password를 해시된 password와 비교
    public static boolean equalsPassword(String password, String encryptPassword) {
        return BCrypt.checkpw(password, encryptPassword);
    }
}
