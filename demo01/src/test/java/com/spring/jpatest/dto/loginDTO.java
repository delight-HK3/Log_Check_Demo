package com.spring.jpatest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class loginDTO {
    private String userId;      // 아이디
    private String password;    // 비밀번호
}
