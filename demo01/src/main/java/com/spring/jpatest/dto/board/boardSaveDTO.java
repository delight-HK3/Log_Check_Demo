package com.spring.jpatest.dto.board;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class boardSaveDTO {
    
    private Long useruuid;          // 유저 일련번호
    private int boardSeq;           // 게시글 일련번호
    private LocalDate instDate;     // 게시글 등록일
    private String boardTitle;      // 게시글 제목
    private String boardSubject;    // 게시글 내용

}
