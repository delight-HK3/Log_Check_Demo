package com.spring.jpatest.dto.login;

import java.util.UUID;

import lombok.Getter;

@Getter
public class loginResponseDTO {
    
    private final UUID useruuid;        // 유저 일련번호호
    private final String userId;        // 아이디
    private final String nickName;      // 닉네임
    private final String password;      // 비밀번호

    public loginResponseDTO(UUID useruuid, String userId, String nickName, String password){
        this.useruuid = useruuid;
        this.userId = userId;
        this.nickName = nickName;
        this.password = password;
    }
    
}
