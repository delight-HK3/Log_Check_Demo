package com.spring.jpatest.config.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginSessionCheckFilter implements Filter{
    private final String[] whitelist = {"/", "/user/*", "/login", "/board/list", "/board/detail", "/js/*","/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI(); // 현재 URL 가져오기

        try {
            //log.info("인증 체크 필터 시작{}", requestURI);
 			if (isLoginCheckPath(requestURI)) {
                //log.info("인증 체크 로직 실행{}", requestURI);
 				HttpSession session = httpRequest.getSession(false); // false로 해도 상관 없고 true도 상관이 없다.
                
                // 세션이 없거나 세션 속성중 id가 없는 경우
		 		if (session == null || session.getAttribute("nickName") == null) {

                    // 로그인이 필요하다는 alert메세지를 만드는 부분
                    response.setContentType("text/html; charset=utf-8");
                    PrintWriter w = response.getWriter();
                    w.write("<script>alert('로그인이 필요합니다.');location.href='/board/list';</script>");
                    w.flush();
                    w.close();

 					return; //여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
				}
    		 }
			 chain.doFilter(request, response); //다음 필터 진행. 없다면 서블릿 띄우기
		 } catch (Exception e) {
 			throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 한다.
		 }

    }

    /**
	 * 화이트 리스트의 경우 인증 체크를 안한다.
	 */
	private boolean isLoginCheckPath(String requestURI) {
 		return !PatternMatchUtils.simpleMatch(whitelist, requestURI); 
        // 입력이 들어온 url이 whiteList에 포함되어있는지 확인
    }
}
