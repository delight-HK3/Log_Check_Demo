package com.spring.jpatest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@EnableJpaAuditing
@Configuration
public class querydslConfig {

    @PersistenceContext
    private EntityManager em; // EntityManager는 JPA에서 엔터티의 생성, 조회, 수정 삭제를 수행

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em){ 
        // 쿼리를 작성하는 JPAQueryFactory에 EntityManager를 넘겨 사용한다.	
        return new JPAQueryFactory(em);
    }
}
