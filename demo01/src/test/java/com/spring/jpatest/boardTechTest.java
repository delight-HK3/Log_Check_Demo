package com.spring.jpatest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.spring.jpatest.domain.Board;
import com.spring.jpatest.dto.board.boardSaveDTO;
import com.spring.jpatest.service.boardService;

@SpringBootTest
@DisplayName("게시판 기능 테스트 클래스")
public class boardTechTest {
    
    @Autowired
	boardService boardService;

    @Test
	@DisplayName("특정 게시글 정보 가져오기 (실패)")
	public void getBoardDetailFailTest(){
		
		//boardDetailDTO result = boardService.getBoardDetail(2);

		//System.out.println(result);

	}

    @Test
	@DisplayName("특정 게시글 정보 가져오기 (성공)")
	public void getBoardDetailSuccessTest(){
		
		int numberOfUsers = 3;

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);
		CountDownLatch latch = new CountDownLatch(numberOfUsers);

        // When
        for (int i = 0; i < numberOfUsers; i++) {
            executorService.submit(() -> {
                try {
                    boardService.boardCntUp(1);
                } catch (Exception e) {
                    // 낙관적 락 충돌로 인한 예외는 예상된 동작이므로 무시
					System.out.println("스레드에서 예외 발생: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        // Then
        //Board board = boardService.getBoardDetail(1);

        // 낙관적 락으로 인해 정확히 10이어야 함 (모든 요청이 성공적으로 반영되어야 함)
        //assertEquals(numberOfUsers, board.getViewCnt());
	}

	@Test
	@DisplayName("특정 게시글 업데이트 (성공)")
	public void updateboardSuccess(){
		
		boardSaveDTO boardDto = new boardSaveDTO();

		boardDto.setBoardSeq(2);
		boardDto.setBoardTitle("1234sadfasdfdsa");
		boardDto.setBoardSubject("1234aegsdfgsdfgsdfg");

		boardService.boardSave(boardDto);

	}

	@Test
	@DisplayName("특정 게시글 업데이트 (실패)")
	public void updateboardFailed(){
		
		boardSaveDTO boardDto = new boardSaveDTO();

		boardDto.setBoardSeq(29);
		boardDto.setBoardTitle("1234sadfasdfdsa");
		boardDto.setBoardSubject("1234aegsdfgsdfgsdfg");

		boardService.boardSave(boardDto);

	}
}
