package com.spring.jpatest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.spring.jpatest.dto.user.userDTO;
import com.spring.jpatest.service.userService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class userController {

    private final userService userService;

    public userController(userService userService){
        this.userService = userService;
    }

    /**
     * user - 유저추가 페이지 이동
     * 
     * @param mav
     * @return
     */
    @RequestMapping(value="/user/add", method=RequestMethod.GET)
    public ModelAndView requestMethodName(userDTO userdto, ModelAndView mav) {

        mav.addObject("userdto", userdto);
        mav.setViewName("user/userAddPage");

        return mav;
    }
    
    /**
     * user - 입력된 정보를 기반으로 유저추가
     * 
     * @param nickName
     * @return
     */
    @RequestMapping(value="/user/inst", method=RequestMethod.POST)
    public String userInst(@Valid @ModelAttribute("userdto") userDTO userdto, BindingResult result) {
        
        if(result.hasErrors()){
            return "user/userAddPage";
        } else {
            userService.useradd(userdto);
            return "redirect:/board/list";
        }
    }

}
