package com.uclass.bookmanager.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.exception.CustomException;
import com.uclass.bookmanager.common.exception.CustomException;

public class AuthenticationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		String[] paths = httpRequest.getRequestURI().split("/");
		
		if(paths[2] != null && (
				paths[2].equals("rent") 
				|| paths[2].equals("notice")
				|| paths[2].equals("mypage")
				)) {
			
			//ajax 통신인 경우
			if(paths[3].equals("multi.do")) {
				if(session.getAttribute("logInInfo") == null) {
					throw new CustomException(ErrorCode.AM01);
				}
			}else {
				if(session.getAttribute("logInInfo") == null) {
					throw new CustomException(ErrorCode.AM01);
				}
			}
		}
		
		chain.doFilter(httpRequest, response);
	}
	
	
	
	
	
	

}
