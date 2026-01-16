package com.spring.jpatest.exception.custom;

import com.spring.jpatest.exception.exceptionEnum;

import lombok.Getter;
/*
 * 작성자가 아닌 다른 유저가 게시글을 삭제하는 경우에 동작하는 Exception 
 */

@Getter
public class NoPermissionsException extends RuntimeException{
    private exceptionEnum error;

    public NoPermissionsException(exceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }

}
