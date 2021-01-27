package com.uclass.bookmanager.exception.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uclass.bookmanager.common.exception.CustomException;

/**
 * Servlet implementation class ErrorHandler
 */
public class ErrorHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//클라이언트가 요청 보낸 URI를 저장
		String[] uriArr = request.getRequestURI().split("/");
		String path = uriArr[3];
		
		// request.getRequestURI() : /bookmanager/index/index
		//0번인덱스 : 공백 
		//1번인덱스 : contextPath
		//2번인덱스 : 경로		
		if(path.equals("ex")) {
			ex(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void ex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		String prefix = "/WEB-INF/views/";
		
		//만약 Exception이 발생하면 
		//request의 javax.servlet.error.exception 이라는 키값으로 담겨온다.
		CustomException e = (CustomException) request.getAttribute(
                "javax.servlet.error.exception");
		
		String contentType = request.getContentType();
		
		//비동기통신 처리
		if(contentType != null && contentType.equals("application/json")) {
			//exception이 발생하면 500상태코드가 전송된다. 200 또는 201 외의 코드로 날아가면
			//error처리가 되기 때문에 필요하다면 200 상태코드로 응답을 전송
			//response.setStatus(200);
			response.setContentType("text/html");
		    response.setCharacterEncoding("utf8");
		    response.getWriter().print(e.ERRORCODE.MESSAGE);
		}else {
			request.setAttribute("alertMsg", e.ERRORCODE.MESSAGE);
			request.setAttribute("url", e.ERRORCODE.URL);
			RequestDispatcher rd = request.getRequestDispatcher(prefix + "common/result.jsp");
			rd.forward(request, response);
		}
	}
}
