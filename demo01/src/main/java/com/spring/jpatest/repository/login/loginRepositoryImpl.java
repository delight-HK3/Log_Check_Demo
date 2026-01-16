package com.spring.jpatest.repository.login;

import static com.spring.jpatest.domain.QUser.user;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.spring.jpatest.dto.login.loginDTO;
import com.spring.jpatest.dto.login.loginResponseDTO;

@Repository
public class loginRepositoryImpl implements loginRepository{

    private final JPAQueryFactory queryFactory;

    public loginRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    @Override
    public loginResponseDTO getUserInfo(loginDTO logindto) {

        loginResponseDTO result = queryFactory
                                    .select(Projections.constructor(loginResponseDTO.class,
                                        user.id
                                        , user.userId
                                        , user.nickName
                                        , user.password
                                    ))
                                    .from(user)
                                    .where(user.userId.eq(logindto.getUserId()))
                                    .fetchOne();
                                    
        return result;
    }
    
}
