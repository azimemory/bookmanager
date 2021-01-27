package com.uclass.bookmanager.rent.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.rent.model.service.RentBookService;
import com.uclass.bookmanager.rent.model.vo.Rent;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.exception.CustomException;
import com.uclass.bookmanager.common.exception.CustomException;

public class RentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String prefix = "/WEB-INF/views/";
	
    public RentController(){
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//클라이언트가 요청 보낸 URI를 저장
		String[] uriArr = request.getRequestURI().split("/");
		String path = uriArr[3];
		// request.getRequestURI() : /bookmanager/index/index
		//0번인덱스 : 공백 
		//1번인덱스 : contextPath
		//2번인덱스 : 경로
		if(path.equals("rent.do")) {
			rentBook(request,response);
		}else if(path.equals("multi.do")) {
			rentBooks(request,response);
		}else if(path.equals("return.do")) {
			returnBook(request,response);
		}else if(path.equals("extend.do")) {
			extendBook(request,response);
		}else if(path.equals("list.do")) {
			rentList(request,response);
		}else if(path.equals("detail.do")) {
			rentDetail(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void rentBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		List<Book> bookList = new ArrayList<Book>();
		String title = request.getParameter("title");
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("logInInfo");
		int rentCnt = member.getRentCnt();
		rentCnt++;
		String bIdx = request.getParameter("bIdx");
		
		Book book = new Book();
		book.setbIdx(bIdx);
		book.setTitle(title);
		bookList.add(book);
		
		if(member.getRentableDate().after(new Date())) {
			throw new CustomException(ErrorCode.IR05);
		}
		
		if(rentCnt > 5 ) {
			throw new CustomException(ErrorCode.IR06);
		}
			
		RentBookService rentBookService = new RentBookService();
		String rentTitle = title;
		Rent rent = new Rent();
		rent.setUserId(member.getUserId());
		rent.setTitle(rentTitle);
		rent.setRentBookList(bookList);
		
		rentBookService.insertRentBookInfo(rent);
		
		member.setRentCnt(member.getRentCnt()+1);
		request.setAttribute("alertMsg", "도서대출에 성공했습니다.");
		request.setAttribute("url", "/rent/list.do");

		RequestDispatcher rd = request.getRequestDispatcher(prefix + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void rentBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
				
		List<Book> bookList = new ArrayList<Book>();
		// 컨텐츠타입이 application/x-www-form-urlencoded 이 아니기 때문에
		// request.getParameter 형태로 데이터를 받을 수 없다.
		// request Message의 body부분에서 직접 데이터를 꺼내여한다.
		// json이 문자열 형태로 오기 때문에 BufferedReader를 사용 해 보자!
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonData = "";
		while(br.ready()) {
			jsonData += br.readLine();
		}
		
		Member member = (Member) request.getSession().getAttribute("logInInfo");
		int rentCnt = member.getRentCnt();
		
		Map<String,List<String>> data = new Gson().fromJson(jsonData,Map.class);
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("utf8");
	    
		int i = 0;
		for(String bIdx : data.get("bIdxs")) {
			Book book = new Book();
			book.setbIdx(bIdx);
			book.setTitle(data.get("titles").get(i++));
			bookList.add(book);
		}
		
		//대출할 도서를 하나도 넘기지 않았으면 대출 불가능
		if(bookList.size() == 0) {
			throw new CustomException(ErrorCode.IR07);
		}
		
		String title =  bookList.get(0).getTitle() + " 외 " + (bookList.size()-1) + "권";
		rentCnt += bookList.size();
		
		Rent rent = new Rent();
		rent.setUserId(member.getUserId());
		rent.setTitle(title);
		rent.setRentBookList(bookList);
		rent.setRentBookCnt(rentCnt);
		
		if(rentCnt > 5 ) {
			throw new CustomException(ErrorCode.IR06);
		}
		
		RentBookService rentBookService = new RentBookService();
		rentBookService.insertRentBookInfo(rent);
		member.setRentCnt(member.getRentCnt()+1);
		response.getWriter().print("대출이 완료되었습니다.");
	}
	
	public void returnBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Member member = (Member) request.getSession().getAttribute("logInInfo");
		
		Rent rent = new Rent();
		rent.setUserId(member.getUserId());
		rent.setRmIdx(request.getParameter("rmIdx"));
		rent.setRbIdx(request.getParameter("rbIdx"));
		
		RentBookService rentBookService = new RentBookService();
		
		rentBookService.updateReturnRentBook(rent);
		member.setRentCnt(member.getRentCnt()-1);
		request.setAttribute("alertMsg", "반납처리가 완료되었습니다.");
		request.setAttribute("url", "/rent/list.do");
		
		RequestDispatcher rd = request.getRequestDispatcher(prefix + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void extendBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String rmIdx = request.getParameter("rmIdx");
		String rbIdx =request.getParameter("rbIdx");
		
		Member member = (Member) request.getSession().getAttribute("logInInfo");
		String memberGrade = member.getGrade();
		RentBookService rentBookService = new RentBookService();
	
		rentBookService.updateExtendRentBook(rmIdx, rbIdx, memberGrade);
		request.setAttribute("alertMsg", "연장처리가 완료되었습니다.");
		request.setAttribute("url", "/rent/list.do");
		
		RequestDispatcher rd = request.getRequestDispatcher(prefix + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void rentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Member member = (Member) request.getSession().getAttribute("logInInfo");
		String cPage = request.getParameter("cPage");
		
		int currentPage = 1;
		
		if(cPage != null) {
			currentPage = Integer.parseInt(cPage);
		}
		
		int cntPerPage = 5;
		
		RentBookService rentBookService = new RentBookService();
		RequestDispatcher rd = null;
		Map<String,Object> commandMap = rentBookService.selectRentinfo(member.getUserId(),currentPage,cntPerPage);

		request.setAttribute("data", commandMap.get("data"));
		request.setAttribute("paging", commandMap.get("paging"));
		rd = request.getRequestDispatcher(prefix + "mypage/rentList.jsp");
		rd.forward(request, response);
	}
	
	public void rentDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String rmIdx = request.getParameter("rmIdx");
		RentBookService rentBookService = new RentBookService();
		List<Map<String,Object>> rentBookList = rentBookService.selectRentinfo("rmIdx", rmIdx);
		request.setAttribute("data", rentBookList);
		
		RequestDispatcher rd = request.getRequestDispatcher(prefix + "mypage/rentDetail.jsp");
		rd.forward(request, response);
	}
}
