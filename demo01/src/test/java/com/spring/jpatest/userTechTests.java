package com.spring.jpatest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.jpatest.dto.login.loginDTO;
import com.spring.jpatest.dto.login.loginResponseDTO;
import com.spring.jpatest.dto.user.userDTO;
import com.spring.jpatest.repository.login.loginRepository;
import com.spring.jpatest.repository.user.userRepository;
import com.spring.jpatest.service.userService;

@SpringBootTest
@DisplayName("로그인 및 유저생성 테스트 클래스")
public class userTechTests {
	
	userService service;
	userRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	loginRepository loginRepository;



	@BeforeEach
    public void beforeEach() {
        service = new userService(repository, passwordEncoder);
    }

	@Test
	@DisplayName("유저정보가 정상일 경우 등록 테스트")
	public void userOk(){

		//System.out.println("test");

		userDTO userdto = new userDTO();

		userdto.setNickName("test1");
		userdto.setPassword("admin");
		userdto.setUserId("tester1");

        service.useradd(userdto);

		System.out.println("완료");
	}


	@Test
	@DisplayName("유저정보가 없는 경우 등록 테스트")
	public void userfalse(){
		
		// 데이터가 없는 경우에는 java.lang.IllegalStateException 발생
		//userDTOTest userdto = new userDTOTest("","","");

	}

	@Test
	@DisplayName("유저정보 가져오기")
	public void getUserInfo(){
		
		loginDTO logindto = new loginDTO();

		logindto.setPassword("user1234");
		logindto.setUserId("user123");

		loginResponseDTO result = loginRepository.getUserInfo(logindto);

		System.out.println(result);

		// 데이터가 없는 경우에는 java.lang.IllegalStateException 발생
		//userDTOTest userdto = new userDTOTest("","","");

	}

}
