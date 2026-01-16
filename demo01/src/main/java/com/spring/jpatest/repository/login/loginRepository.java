package com.spring.jpatest.repository.login;

import org.springframework.stereotype.Repository;

import com.spring.jpatest.dto.login.loginDTO;
import com.spring.jpatest.dto.login.loginResponseDTO;

@Repository
public interface loginRepository {

    public loginResponseDTO getUserInfo(loginDTO logindto); // 입력한 유저정보와 일치한 Row 리턴
    
} 
    
    


