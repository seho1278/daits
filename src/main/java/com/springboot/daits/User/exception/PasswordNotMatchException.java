package com.springboot.daits.User.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String s) {
        super(s);
    }
}
