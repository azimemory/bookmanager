package com.uclass.bookmanager.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//요청온 데이터의 인코딩을 UTF-8로 변경
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}
}
