package com.spring.jpatest.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jpatest.domain.User;

@Repository
public interface userRepository extends JpaRepository<User, Long>{  
    
}
