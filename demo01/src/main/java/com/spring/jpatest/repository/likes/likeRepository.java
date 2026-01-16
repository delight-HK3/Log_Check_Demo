package com.spring.jpatest.repository.likes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jpatest.domain.Board;
import com.spring.jpatest.domain.Likes;
import com.spring.jpatest.domain.User;

@Repository
public interface likeRepository extends JpaRepository<Likes, Long>{

    Optional<Likes> findByUserAndBoard(User user, Board board);
    
} 
