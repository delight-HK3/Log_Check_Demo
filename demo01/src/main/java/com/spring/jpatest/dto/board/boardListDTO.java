package com.spring.jpatest.dto.board;

import java.time.LocalDate;

import lombok.Getter;

/**
 * 게시판 조회 용도 DTO
 */
@Getter
public class boardListDTO {
    
    private final int boardNum;
    private final String boardTitle;
    private final int viewCnt; 
    private final int likeCnt;
    private final LocalDate instDate;
    private final String nickName;

    public boardListDTO(int boardNum, String boardTitle, int viewCnt,
                    int likeCnt, LocalDate instDate, String nickName){
        this.boardNum = boardNum;
        this.boardTitle = boardTitle;
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.instDate = instDate;
        this.nickName = nickName;
    } 
}
