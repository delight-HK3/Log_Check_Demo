package com.spring.jpatest.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.spring.jpatest.domain.Board;
import com.spring.jpatest.dto.board.boardDetailDTO;
import com.spring.jpatest.dto.board.boardListDTO;
import com.spring.jpatest.dto.board.boardSaveDTO;
import com.spring.jpatest.service.boardService;
import com.spring.jpatest.service.likeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class boardController {

    private final boardService boardservice;
    private final likeService likeService;

    public boardController(boardService boardservice, likeService likeService){
        this.boardservice = boardservice;
        this.likeService = likeService;
    }

    /**
     * board - 메인 게시판 로딩
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value = "/board/list", method=RequestMethod.GET)
    public ModelAndView boardMain(ModelAndView mav, @PageableDefault(size = 5,page = 0) Pageable pageable) {
        
        Page<boardListDTO> boardList = boardservice.getBoardList(pageable);

        mav.addObject("boardList", boardList);
        mav.addObject("maxPage", 5); // 페이지네이션 하나에 출력시킬 페이지 수
        mav.addObject("currentPage", pageable.getPageNumber()); // 현재 페이지
        mav.addObject("totalPages", boardList.getTotalPages()); // 페이지 전체개수

        mav.setViewName("board/boardListPage");

        return mav;
    }

    /**
     * board - 메인 게시글 상세보기, 조회수 1 증가
     * 
     * @param mav
     * @param boardCd
     * @return
     */
    @RequestMapping(value="/board/detail", method=RequestMethod.GET)
    public ModelAndView boardDetailPage(ModelAndView mav, @RequestParam(value="boardCd") int boardCd, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long userid = (Long) session.getAttribute("useruuid");

        Board boardDetail = boardservice.getBoardDetail(boardCd, userid);
        
        if(userid != null){
            boolean likeCheck = likeService.likeSearch(boardCd, userid);
            mav.addObject("likeCheck", likeCheck);
        }
        mav.addObject("boardDetail", boardDetail);
        mav.setViewName("board/boardDetailPage");

        return mav;
    }

    /**
     * board - 게시글 입력 페이지 로딩
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value="/board/add", method=RequestMethod.GET)
    public ModelAndView boardAddPage(boardSaveDTO boardSavedto, ModelAndView mav) {

        mav.addObject("boarddto", boardSavedto);
        mav.setViewName("board/boardAddPage");

        return mav;
    }

    /**
     * board - 게시글 수정 페이지 로딩
     * 
     * @param mav
     * @param boardCd
     * @return
     */
    @RequestMapping(value="/board/edit", method=RequestMethod.GET)
    public ModelAndView boardEditPage(@RequestParam int boardCd, boardSaveDTO boardSavedto, ModelAndView mav) {

        boardDetailDTO boardEdit = boardservice.getBoardEdit(boardCd);
        
        mav.addObject("boardEdit", boardEdit);
        mav.addObject("boarddto", boardSavedto);
        mav.setViewName("board/boardEditPage");

        return mav;
    }

    /**
     * board - 게시글 내용 등록 / 수정
     * 
     * @param boardSavedto
     * @param request
     * @return
     */
    @RequestMapping(value="/board/save", method=RequestMethod.POST)
    public String boardSave(@ModelAttribute("boarddto") boardSaveDTO boardSavedto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        boardSavedto.setUseruuid((Long) session.getAttribute("useruuid"));
        
        boardservice.boardSave(boardSavedto); // 게시글 내용 등록

        return "redirect:/board/list";
    }
    
    /**
     * board - 게시글 삭제
     * 
     * @param boardCd
     * @param request
     * @return
     */
    @RequestMapping(value="/board/del", method=RequestMethod.DELETE)
    public String boardDel(@RequestParam int boardCd, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long userid = (Long) session.getAttribute("useruuid");
        
        boardservice.boardDel(userid, boardCd); // 선택한 게시글 삭제

        return "redirect:/board/list";
    }
    
}
