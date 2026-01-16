package com.spring.jpatest.dto.login;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 용도 DTO
 */
@Getter
@Setter
public class loginDTO {
    
    private String userId;      // 아이디
    private String password;    // 비밀번호

}
