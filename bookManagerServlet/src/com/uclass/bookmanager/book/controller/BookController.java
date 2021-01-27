package com.uclass.bookmanager.book.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uclass.bookmanager.book.model.service.BookService;
import com.uclass.bookmanager.book.model.vo.Book;

public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String prefix = "/WEB-INF/views/";
	
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// TODO Auto-generated method stub
		//클라이언트가 요청 보낸 URI를 저장
		String[] uriArr = request.getRequestURI().split("/");
		String path = uriArr[3];
		// request.getRequestURI() : /bookmanager/index/index
		//0번인덱스 : 공백 
		//1번인덱스 : contextPath
		//2번인덱스 : 경로
		if(path.equals("search.do")) {
			search(request,response);
		}else if(path.equals("detail.do")) {
			detail(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String keyword = request.getParameter("keyword");
		BookService bookService = new BookService();
		List<Book> bookList = bookService.selectBookList(keyword);
		
		RequestDispatcher rd = null;
		request.setAttribute("bookList", bookList);
		rd = request.getRequestDispatcher(prefix + "book/bookSearch.jsp");
		rd.forward(request, response);
	}
	
	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String bIdx = request.getParameter("bIdx");
		BookService bookService = new BookService();
		Book book = bookService.selectBook(bIdx);
		
		RequestDispatcher rd = null;
		request.setAttribute("book", book);
		rd = request.getRequestDispatcher(prefix + "book/bookDetail.jsp");
		rd.forward(request, response);
	}
}
