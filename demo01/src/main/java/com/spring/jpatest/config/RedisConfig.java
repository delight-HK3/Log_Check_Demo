package com.spring.jpatest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // RedisTemplate : Spring data Redis에서 제공하는 Redis를 제어하기위한 클래스
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer()); 
        // StringRedisSerializer : 문자열을 바이트 코드로 변환하는 직렬화 도구
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // 조회수 및 좋아요의 Key값 타입이 String이기에 타입변환을 위해 추가
        
        return redisTemplate;
    } 
}
