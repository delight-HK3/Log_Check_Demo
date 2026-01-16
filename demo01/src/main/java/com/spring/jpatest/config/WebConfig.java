package com.spring.jpatest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.jpatest.config.filter.LoginSessionCheckFilter;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    // FilterRegistrationBean가 제네릭을 사용하기에 필터의 타입이 정해져있지 않다는 경고 메세지
    @SuppressWarnings("rawtypes")
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new LoginSessionCheckFilter()); // 세션이 없으면 게시글작성을 못한다.
        filterRegistrationBean.setOrder(1); // 필터 체인할 때 가장 먼저 실행
        filterRegistrationBean.addUrlPatterns("/*"); //모든 요청 url에 대해 실행
        
        return filterRegistrationBean;
    }
}
