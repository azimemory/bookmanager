package com.kh.bookmanager.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		HttpSession session = request.getSession();
		System.out.println(authentication.getPrincipal());
		session.setAttribute("logInInfo", authentication.getPrincipal());
		response.sendRedirect(request.getContextPath()+"/index/index.do");
		
	}
}
