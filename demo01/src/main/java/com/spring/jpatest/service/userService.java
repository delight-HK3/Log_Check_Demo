package com.spring.jpatest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpatest.domain.User;
import com.spring.jpatest.dto.user.userDTO;
import com.spring.jpatest.repository.user.userRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class userService {
    
    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public userService(userRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 사용자 등록
     * 
     * @param userdto
     */
    @Transactional // 쓰기 작업이므로 트랜잭션 필요
    @CachePut(value = "users", key = "#result.id")
    public void useradd(userDTO userdto){
        User user = User.builder()
                        .nickName(userdto.getNickName())
                        .userId(userdto.getUserId())
                        .password(passwordEncoder.encode(userdto.getPassword()))
                        .build();
        
        userRepository.save(user);
    }
    
    @Transactional
    @CachePut(value = "users", key = "#id")
    public void userUpdate(userDTO userdto, Long id){
        Optional<User> user = userRepository.findById(id);
        
        
    }

    /**
     * 사용자 1명 조회 후 캐시에 등록
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @CachePut(value = "users", key = "#id")
    public Optional<User> getUserById(Long id){
        System.out.println("데이터베이스에서 사용자 ID: " + id + " 조회 중...");
        return userRepository.findById(id);
    }

    /**
     * 사용자 1명 삭제 후 캐시에서도 삭제
     * 
     * @param id
     */
    @Transactional // 쓰기 작업이므로 트랜잭션 필요
    @CacheEvict(value = "users", key = "#id") // 해당 ID의 캐시 엔트리 제거
    public void deleteUser(Long id) {
        System.out.println("데이터베이스에서 사용자 ID: " + id + " 삭제 중...");
        userRepository.deleteById(id);
        System.out.println("캐시에서 사용자 ID: " + id + " 무효화 완료.");
    }

    /**
     * 모든 사용자 캐시를 무효화합니다.
     * 
     */
    @CacheEvict(value = "users", allEntries = true)
    public void clearAllUsersCache() {
        System.out.println("모든 사용자 캐시 무효화 명령 실행.");
    }
}
