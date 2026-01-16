package com.spring.jpatest.repository.board;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpatest.domain.Board;
import com.spring.jpatest.domain.User;
import com.spring.jpatest.dto.board.boardDetailDTO;
import com.spring.jpatest.dto.board.boardListDTO;
import com.spring.jpatest.dto.board.boardSaveDTO;
import com.spring.jpatest.exception.exceptionEnum;
import com.spring.jpatest.exception.custom.NoBoardDataException;
import com.spring.jpatest.exception.custom.NoPermissionsException;
import com.spring.jpatest.exception.custom.NoUserDataException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static com.spring.jpatest.domain.QBoard.board;
import static com.spring.jpatest.domain.QUser.user;

@Repository
public class boardRepositoryImpl implements boardRepositoryCustom{
    
    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    public boardRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<boardListDTO> getBoardList(Pageable pageable) {
        List<boardListDTO> result = queryFactory
                                    .select(Projections.constructor(boardListDTO.class,
                                        board.seq
                                        , board.boardTitle
                                        , board.viewCnt
                                        , board.likeCnt
                                        , board.instDate
                                        , user.nickName
                                    ))
                                    .from(board)
                                    .join(board.user, user)
                                    .orderBy(board.seq.desc()) // 역순 정렬
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .fetch();
        
        JPQLQuery<Board> count = queryFactory.selectFrom(board);

        // getPage(결과목록, 페이지네이션 설정, 조건에 맞는 데이터 총 개수)
        // 성능 최적화를 위해 PageImpl대신 PageableExecutionUtils사용
        // (PageImpl에서 DB 3번 호출할 때 PageableExecutionUtils는 2번 호출)
        return PageableExecutionUtils.getPage(result, pageable, () -> count.fetchCount());
    }

    @Override
    public Optional<boardDetailDTO> getBoardDetail(int boardCd) {

        Optional<boardDetailDTO> result = 
                            Optional.ofNullable(queryFactory
                                    .select(Projections.constructor(boardDetailDTO.class, 
                                        board.seq
                                        , user.nickName
                                        , board.boardTitle
                                        , board.boardSubject
                                        , board.viewCnt
                                        , board.likeCnt
                                        , board.instDate
                                    ))
                                    .from(board)
                                    .where(board.seq.eq(boardCd))
                                    .join(board.user, user)
                                    .fetchOne());
                                    
        return result;
    }
    
    @Override
    @Transactional
    public void boardCntUp(int boardCd) {
        
        try{
            // 조건과 일치하는 게시글 검색
            Board detail = queryFactory.selectFrom(board)
                                        .where(board.seq.eq(boardCd))
                                        .fetchOne();
            
            // 동시성 문제 고민
            // Redis 도입해보기
            detail.plusViewCnt();

        } catch (NullPointerException e) {
            //e.printStackTrace();
            em.close();
            em.getTransaction().rollback(); // 롤백

            throw new NullPointerException();
        } 

        em.close(); // 사용한 entityManager 닫기
    }

    @Override
    @Transactional
    public void boardSave(boardSaveDTO boardSavedto) {

        try{
            if(boardSavedto.getBoardSeq() == 0){
                // 조건과 일치하는 유저 검색
                User userOne = queryFactory.selectFrom(user)
                                        .where(user.id.eq(boardSavedto.getUseruuid()))
                                        .fetchOne();

                Board board = Board.builder()
                                .user(userOne)
                                .boardTitle(boardSavedto.getBoardTitle())
                                .boardSubject(boardSavedto.getBoardSubject())
                                .instDate(boardSavedto.getInstDate())
                                .build();

                em.persist(board);

            } else {
                // 조건과 일치하는 게시글 검색
                Board boardOne = queryFactory.selectFrom(board)
                                        .where(board.seq.eq(boardSavedto.getBoardSeq()))
                                        .fetchOne();

                boardOne.updateBoard(boardSavedto.getBoardTitle(), boardSavedto.getBoardSubject());

                em.merge(boardOne);
            }      

        } catch (NullPointerException e) {
            //e.printStackTrace();
            em.close();
            em.getTransaction().rollback(); // DB 롤백

            if(boardSavedto.getBoardSeq() == 0){ // 유저정보가 없는 경우
                throw new NoUserDataException(exceptionEnum.NO_USER_DATA);
            } else { // 게시글의 정보가 없는 경우
                throw new NoBoardDataException(exceptionEnum.NO_BOARD_DATA);
            }
        } 
        
        em.close(); // 사용한 entityManager 닫기
    }

    @Override
    @Transactional
    public void boardDelete(Long userid, int boardCd) {
        try{
            // 조건과 일치하는 유저 검색
            Board boardOne = queryFactory.selectFrom(board)
                            .where(user.id.eq(userid), board.seq.eq(boardCd))
                            .fetchOne();

            em.remove(boardOne);

        } catch (NullPointerException e) {
            // 현재 존재하는 세션과 삭제하려고 하는 게시글의 작성자가 다른경우
            //e.printStackTrace();
            em.close();
            em.getTransaction().rollback();

            throw new NoPermissionsException(exceptionEnum.NO_PERMISSION);
        } 
        
        em.close(); // 사용한 entityManager 닫기
    }

}
