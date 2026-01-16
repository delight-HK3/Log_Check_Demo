package com.spring.jpatest.repository.board;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jpatest.domain.Board;

@Repository
@Primary
public interface boardRepository extends JpaRepository<Board, Integer>, boardRepositoryCustom{

} 
