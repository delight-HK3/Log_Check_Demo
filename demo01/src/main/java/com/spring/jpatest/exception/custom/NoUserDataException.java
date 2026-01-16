package com.spring.jpatest.exception.custom;

import java.util.UUID;

import com.spring.jpatest.exception.exceptionEnum;

import lombok.Getter;

@Getter
public class NoUserDataException extends RuntimeException{
     private exceptionEnum error;

    public NoUserDataException(exceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }

    public NoUserDataException(Long userId){
        
    }
}
