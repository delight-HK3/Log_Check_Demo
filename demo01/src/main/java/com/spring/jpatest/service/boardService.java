package com.spring.jpatest.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpatest.domain.Board;
import com.spring.jpatest.dto.board.boardDetailDTO;
import com.spring.jpatest.dto.board.boardListDTO;
import com.spring.jpatest.dto.board.boardSaveDTO;
import com.spring.jpatest.exception.exceptionEnum;
import com.spring.jpatest.exception.custom.NoBoardDataException;
import com.spring.jpatest.repository.board.boardRepository;
import com.spring.jpatest.repository.user.userRepository;

@Service
public class boardService {
    
    private final boardRepository boardRepository;
    private final userRepository userRepository;
    private final viewCountService viewCountService;

    public boardService(boardRepository boardRepository, userRepository userRepository, viewCountService viewCountService){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.viewCountService = viewCountService;
    }
    
    /**
     * 게시판 - 게시글 모두로딩
     * 
     * @return resultList
     */
    public Page<boardListDTO> getBoardList(Pageable pageable){
        return boardRepository.getBoardList(pageable);
    }

    /**
     * 게시판 - 게시글 상세보기, 조회수 1 증가
     * 
     * @param boardCd
     * @return result
     */
    
    @Transactional
    public Board getBoardDetail(int boardCd, Long userid){

        Board board = boardRepository.findById(boardCd)
                .orElseThrow(() -> new NoBoardDataException(exceptionEnum.NO_BOARD_DATA));

        try {
            if(!userid.equals(board.getUser().getId())){
                board.plusViewCnt();
                boardRepository.save(board);
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            System.out.println("낙관적 락 충돌 발생: " + e.getMessage());
            // 필요에 따라 재시도 로직 또는 예외 처리
            throw new RuntimeException("동시성 문제로 인해 조회수 업데이트에 실패했습니다. 다시 시도해주세요.", e);
        } 

        return board;
    }

    @Transactional
    public void boardCntUp(int boardCd){

        Board board = boardRepository.findById(boardCd)
                .orElseThrow(() -> new NoBoardDataException(exceptionEnum.NO_BOARD_DATA));

        board.plusViewCnt();
        
        try {
            boardRepository.save(board);
        } catch (ObjectOptimisticLockingFailureException e) {
            System.out.println("낙관적 락 충돌 발생: " + e.getMessage());
            // 필요에 따라 재시도 로직 또는 예외 처리
            throw new RuntimeException("동시성 문제로 인해 조회수 업데이트에 실패했습니다. 다시 시도해주세요.", e);
        }
    }

    /**
     * 게시판 - 게시글 수정정보 조회
     * 
     * @param boardCd
     * @return
     */
    public boardDetailDTO getBoardEdit(int boardCd){

        boardDetailDTO board = boardRepository.getBoardDetail(boardCd)
                .orElseThrow(() -> new NoBoardDataException(exceptionEnum.NO_BOARD_DATA));

        return board;
    }

    /**
     * 게시판 - 게시글 저장 및 수정
     * 
     * @param boardAdddto
     */
    public void boardSave(boardSaveDTO boardSavedto){
        boardRepository.boardSave(boardSavedto);
    }
    
    /**
     * 게시판 - 게시글 삭제
     * 
     * @param userid
     * @param boardCd
     */
    public void boardDel(Long id, int boardCd){
        boardRepository.boardDelete(id, boardCd);
    }

}
