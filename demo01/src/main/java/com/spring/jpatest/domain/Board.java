package com.spring.jpatest.domain;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    @Comment(value = "일련번호")
    private int seq;                    

    @Comment(value = "유저번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    private User user;

    @Column(name = "boardTitle", length = 50)
    @Comment(value = "게시글제목")
    private String boardTitle;

    @Column(name = "boardSubject")
    @Comment(value = "게시글내용")
    private String boardSubject;

    @Column(name = "viewCnt")
    @Comment(value = "게시글조회수")
    private int viewCnt = 0;

    @Column(name = "likeCnt")
    @Comment(value = "게시글좋아요 개수")
    private int likeCnt = 0;
    
    @Version
    private Long version; // 낙관적 락

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @Column(name = "instDate")
    @Comment(value = "작성일")
    private LocalDate instDate;

    @Builder
    public Board(User user, String boardTitle, String boardSubject, LocalDate instDate){
        this.user = user;
        this.boardTitle = boardTitle;
        this.boardSubject = boardSubject;
        this.instDate = instDate;
    }

    public void updateBoard(String boardTitle, String boardSubject){
        this.boardTitle = boardTitle;
        this.boardSubject = boardSubject;
    }

    public void plusViewCnt(){ // 조회수 1 증가
        this.viewCnt++;
    }

    public void plusLikecount(){ // 좋아요 1 증가
        this.likeCnt++;
    }

    public void minerLikecount(){ // 좋아요 1 제거
        if(this.likeCnt > 0){
            this.likeCnt--;
        }
    }

}
