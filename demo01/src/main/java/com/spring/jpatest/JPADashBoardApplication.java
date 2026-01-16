package com.spring.jpatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JPADashBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(JPADashBoardApplication.class, args);
	}

}
