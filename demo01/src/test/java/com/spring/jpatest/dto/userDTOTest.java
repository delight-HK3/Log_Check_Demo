package com.spring.jpatest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDTOTest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "아이디는 특수문자를 제외해야 합니다.")    
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")   
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영문 대 소문자, 숫자, 특수문자를 사용하세요.")    
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외해야 합니다.")
    public String nickName;

    public userDTOTest(String userId, String password, String nickName){
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
    }
}
