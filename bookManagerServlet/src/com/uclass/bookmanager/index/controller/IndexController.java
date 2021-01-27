package com.uclass.bookmanager.index.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.bookmanager.index.model.service.IndexService;

public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String prefix = "/WEB-INF/views/";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//클라이언트가 요청 보낸 URI를 저장
		String[] uriArr = request.getRequestURI().split("/");
		// request.getRequestURI() : /bookmanager/index/index
		//0번인덱스 : 공백 
		//1번인덱스 : contextPath
		//2번인덱스 : 경로
		if(uriArr[3].equals("index.do")) {
			index(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		IndexService indexService = new IndexService();
		Map<String,List<Book>> books = indexService.selectBooksForIndex();
		//장바구니를 위한 session 생성
		HttpSession session = request.getSession();
		request.setAttribute("books", books);
		RequestDispatcher rd = request.getRequestDispatcher(prefix + "index/index.jsp");
		rd.forward(request, response);
	}
}
