package com.spring.jpatest.repository.board;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.spring.jpatest.dto.board.boardDetailDTO;
import com.spring.jpatest.dto.board.boardListDTO;
import com.spring.jpatest.dto.board.boardSaveDTO;
@Repository
public interface boardRepositoryCustom{

    public Page<boardListDTO> getBoardList(Pageable pageable); // 리스트 조회
    
    public Optional<boardDetailDTO> getBoardDetail(int boardCd); // 게시글 상세정보 호출

    public void boardCntUp(int boardCd); // 게시글 조회수 1 증가

    public void boardSave(boardSaveDTO boardSavedto); // 등록및 저장

    public void boardDelete(Long userid, int boardCd); // 게시글 삭제
} 