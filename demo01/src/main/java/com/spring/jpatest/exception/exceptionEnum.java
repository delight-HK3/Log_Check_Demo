package com.spring.jpatest.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum exceptionEnum {
    
    // 유저등록시 값이 없는 경우
    USER_INSERT_DATA_NULL(HttpStatus.NOT_FOUND,"유저 등록에 필요한 정보가 부족합니다."),
    
    // 아이디 혹은 비밀번호가 일치하지 않은 경우
    NOT_MATCH_INFO(HttpStatus.NOT_FOUND, "아이디 혹은 비밀번호가 올바르지 않습니다."),

    // 유저값이 존재하지 않는 경우
    NO_USER_DATA(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),

    // 게시글이 존재하지 않는 경우
    NO_BOARD_DATA(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다."),

    // 다른 유저가 게시글을 삭제하는 경우
    NO_PERMISSION(HttpStatus.UNAUTHORIZED, "삭제권한이 존재하지 않습니다."),

    // 이미 좋아요를 클릭한 경우
    ALREADY_LIKE(HttpStatus.BAD_REQUEST,"이미 좋아요를 눌렀습니다."),

    // 조건에 맞는 좋아요를 찾을 수 없는 경우
    NO_FIND_LIKE(HttpStatus.NOT_FOUND,"좋아요를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    exceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
