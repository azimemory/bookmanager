package com.uclass.bookmanager.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.uclass.bookmanager.member.model.service.MemberService;
import com.uclass.bookmanager.member.model.vo.Member;

public class MemberController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	final String PREFIX = "/WEB-INF/views/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
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
		if(path.equals("join.do")) {
			joinPage(request,response);
		}else if(path.equals("joinimpl.do")) {
			join(request,response);
		}else if(path.equals("idcheck.do")) {
			checkId(request,response);
		}else if(path.equals("login.do")) {
			login(request,response);
		}else if(path.equals("loginimpl.do")) {
			loginImpl(request,response);
		}else if(path.equals("logout.do")) {
			logout(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void joinPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "member/join.jsp");
		rd.forward(request, response);
	}
	
	public void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		MemberService memberService = new MemberService();
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String tell = request.getParameter("tell");
		
		Member member = new Member();
		member.setUserId(userId);
		member.setPassword(password);
		member.setEmail(email);
		member.setTell(tell);
		
		memberService.insertMember(member);
		request.setAttribute("alertMsg", "회원가입에 성공하였습니다.");
		request.setAttribute("url", "/member/login.do");
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "member/login.jsp");
		rd.forward(request, response);
	}
	
	public void loginImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		MemberService memberService = new MemberService();
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String sessionId = request.getSession().getId();
		
		Member member = memberService.selectMember(userId, password, sessionId);
		
		//대출중인 도서권수를 세션에 저장한다.
		int rentBookCount = memberService.selectRentCount(userId);
		HttpSession session = request.getSession(false);
		member.setRentCnt(rentBookCount);
		session.setAttribute("logInInfo", member);
		request.setAttribute("alertMsg", "로그인에 성공하였습니다.");
		request.setAttribute("url", "/index/index.do");
		
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession(false);
		session.removeAttribute("logInInfo");
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "index/index.jsp");
		rd.forward(request, response);
	}
	
	public void checkId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		// 컨텐츠타입이 application/x-www-form-urlencoded 이 아니기 때문에
		// request.getParameter 형태로 데이터를 받을 수 없다.
		// request Message의 body부분에서 직접 데이터를 꺼내여한다.
		// json이 문자열 형태로 오기 때문에 BufferedReader를 사용 해 보자!
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonData = "";
		while(br.ready()) {
			jsonData += br.readLine();
		}
		
		System.out.println(jsonData);
		
		Map<String,String> data = new Gson().fromJson(jsonData,Map.class);
		response.setContentType("text/html");
	    response.setCharacterEncoding("utf8");
		
		MemberService memberService = new MemberService();
		memberService.selectId(data.get("userId"));
		response.getWriter().print("사용 가능한 아이디 입니다.");
	}
}
