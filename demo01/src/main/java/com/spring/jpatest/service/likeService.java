package com.spring.jpatest.service;

import org.springframework.stereotype.Service;

import com.spring.jpatest.domain.Board;
import com.spring.jpatest.domain.Likes;
import com.spring.jpatest.domain.User;
import com.spring.jpatest.dto.Like.likeRequestDTO;
import com.spring.jpatest.exception.exceptionEnum;
import com.spring.jpatest.exception.custom.AlreadyLikeException;
import com.spring.jpatest.exception.custom.NoBoardDataException;
import com.spring.jpatest.exception.custom.NoUserDataException;
import com.spring.jpatest.exception.custom.NofindLikeException;
import com.spring.jpatest.repository.board.boardRepository;
import com.spring.jpatest.repository.likes.likeRepository;
import com.spring.jpatest.repository.user.userRepository;

@Service
public class likeService {
    
    private final boardRepository boardRepository;
    private final userRepository userRepository;
    private final likeRepository likeRepository;

    public likeService(boardRepository boardRepository, userRepository userRepository, likeRepository likeRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    /**
     * 게시글의 좋아요 여부 확인
     * 
     * @param boardCd
     * @param checkId
     * @return
     */
    public boolean likeSearch(int boardCd, Long checkId){

        User user = userRepository.findById(checkId) // 유저정보 찾기
                        .orElseThrow(() -> new NoUserDataException(checkId)) ;

        Board board = boardRepository.findById(boardCd) // 게시글 찾기
                        .orElseThrow(() -> new NoBoardDataException(boardCd));

        return likeRepository.findByUserAndBoard(user, board).isPresent();
    }

    /**
     * 좋아요 클릭
     * 
     * @param likeRequestdto
     */
    public void likeInsert(likeRequestDTO likeRequestdto){

        User user = userRepository.findById(likeRequestdto.getUseruuId()) // 유저정보 찾기
                        .orElseThrow(() -> new NoUserDataException(likeRequestdto.getUseruuId())) ;

        Board board = boardRepository.findById(likeRequestdto.getBoardCd()) // 게시글 찾기
                        .orElseThrow(() -> new NoBoardDataException(likeRequestdto.getBoardCd()));

        // 이미 좋아요를 클릭한 경우
        if(likeRepository.findByUserAndBoard(user, board).isPresent()){
            throw new AlreadyLikeException(exceptionEnum.ALREADY_LIKE);
        }

        // 낙관적락 try-catch 추가
        board.plusLikecount();
        likeRepository.save(new Likes(user, board));
        boardRepository.save(board);
    }

    /**
     * 좋아요 취소
     * 
     * @param likeRequestdto
     */
    public void likedelete(likeRequestDTO likeRequestdto){

        User user = userRepository.findById(likeRequestdto.getUseruuId()) // 유저정보 찾기
                        .orElseThrow(() -> new NoUserDataException(likeRequestdto.getUseruuId())) ;

        Board board = boardRepository.findById(likeRequestdto.getBoardCd()) // 게시글 찾기
                        .orElseThrow(() -> new NoBoardDataException(likeRequestdto.getBoardCd()));
        
        Likes likes = likeRepository.findByUserAndBoard(user, board) // 누른 좋아요 찾기
                        .orElseThrow(() -> new NofindLikeException(exceptionEnum.NO_FIND_LIKE));           
        
        // 낙관적락 try-catch 추가
        board.minerLikecount();
        likeRepository.delete(likes);
        boardRepository.save(board);
    }
}
