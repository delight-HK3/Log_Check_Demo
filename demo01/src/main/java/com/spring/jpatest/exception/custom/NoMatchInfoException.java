package com.spring.jpatest.exception.custom;

import com.spring.jpatest.exception.exceptionEnum;

import lombok.Getter;
/*
 * 로그인 시 아이디 혹은 비밀번호가 다른 경우에 동작하는 Exception
 */
@Getter
public class NoMatchInfoException extends RuntimeException{
    private exceptionEnum error;

    public NoMatchInfoException(exceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
