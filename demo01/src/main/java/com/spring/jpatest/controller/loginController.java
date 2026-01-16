package com.spring.jpatest.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.jpatest.dto.login.loginDTO;
import com.spring.jpatest.dto.login.loginResponseDTO;
import com.spring.jpatest.service.loginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class loginController {

    private final loginService loginService;

    public loginController(loginService loginService){
        this.loginService = loginService;
    }

     /**
      * login - 로그인 기능
      *
      * @param logindto
      * @param request
      */
    @ResponseBody
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String userlogin(loginDTO logindto, HttpServletRequest request) {

        loginResponseDTO result = loginService.getUserInfo(logindto);

        // 세션이 없으면 새로운 세션을 생성해서 반환
        HttpSession session = request.getSession(true); 
        session.setAttribute("nickName",result.getNickName());
        session.setAttribute("useruuid",result.getUseruuid());
        
        return "success";
    }
    
    /**
     * login - 로그아웃 기능
     * 
     * @param request
     * @return
     */
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String userlogout(HttpServletRequest request) {

        // 세션이 없으면 새로운 세션을 생성하지않고 null을 반환
        HttpSession session = request.getSession(false); 

        if(session != null) {
			session.invalidate();
		}

        return "redirect:/board/list";
    }
}
