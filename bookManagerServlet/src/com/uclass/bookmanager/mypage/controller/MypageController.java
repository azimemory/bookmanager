package com.uclass.bookmanager.mypage.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.mypage.model.service.MypageService;

/**
 * Servlet implementation class MypageController
 */
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String PREFIX = "/WEB-INF/views/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MypageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		//클라이언트가 요청 보낸 URI를 저장
		String[] uriArr = request.getRequestURI().split("/");
		String path = uriArr[3];
		
		// request.getRequestURI() : /bookmanager/index/index
		//0번인덱스 : 공백 
		//1번인덱스 : contextPath
		//2번인덱스 : 경로		
		if(path.equals("mypage.do")) {
			 mypage(request,response);
		}else if(path.equals("modify.do")) {
			modify(request,response);
		}else if(path.equals("leave.do")) {
			memberLeave(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void mypage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "mypage/mypage.jsp");
		rd.forward(request, response);
	}
	
	public void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MypageService mypageService = new MypageService();
		
		Member member = new Member();
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String tell = request.getParameter("tell");
		
		Member sessionMember
			= (Member) request.getSession().getAttribute("logInInfo");
		
		member.setUserId(sessionMember.getUserId());
		member.setPassword(password);
		member.setEmail(email);
		member.setTell(tell);
		
		mypageService.updateMember(member);
		
		//회원 수정에 성공했다면
		request.setAttribute("alertMsg", "회원정보 수정에 성공하였습니다.");
		request.setAttribute("url", "/mypage/mypage.do");
		
		sessionMember.setPassword(member.getPassword());
		sessionMember.setEmail(member.getEmail());
		sessionMember.setTell(member.getTell());
		
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void memberLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MypageService mypageService = new MypageService();
		Member member = (Member)request.getSession().getAttribute("logInInfo");
		
		mypageService.updateMemberToLeave(member.getUserId());
		
		request.setAttribute("alertMsg", "회원탈퇴에 성공하였습니다.");
		request.setAttribute("url","/index/index.do");
		request.getSession().removeAttribute("logInInfo");
		
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
		rd.forward(request, response);
	}
	

}
