package com.spring.jpatest.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 용도 DTO
 */
@Getter
@Setter
public class userDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = ".*[a-zA-Z0-9].*", message = "아이디는 특수문자를 제외해야 합니다.")
    @Size(min=3, max=10, message = "아이디는 3~10자 까지 입니다.")	    
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")   
    @Pattern(regexp = ".*[a-zA-Z0-9].*", message = "비밀번호는 특수문자를 제외해야 합니다.")    
    @Size(min=8, max=16, message = "비밀번호는 8~16자 까지 입니다.")	
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = ".*[a-zA-Z0-9].*", message = "닉네임은 특수문자를 제외해야 합니다.")
    @Size(min=3, max=10, message = "닉네임은 3~10자 까지 입니다.")	
    public String nickName;
}
