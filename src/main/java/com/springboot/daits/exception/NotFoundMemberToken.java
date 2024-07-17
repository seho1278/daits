package com.springboot.daits.exception;

public class NotFoundMemberToken extends RuntimeException {
    public NotFoundMemberToken(String s) { super(s); }
}
