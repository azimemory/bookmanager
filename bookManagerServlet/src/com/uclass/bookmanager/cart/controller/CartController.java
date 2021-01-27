package com.uclass.bookmanager.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uclass.bookmanager.cart.model.service.CartService;
import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.exception.CustomException;

/**
 * Servlet implementation class CartController
 */
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String prefix = "/WEB-INF/views/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
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
		if(path.equals("cart.do")) {
			cart(request,response);
		}else if(path.equals("addCart.do")) {
			addCart(request,response);
		}else if(path.equals("removeCart.do")) {
			removeCart(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Member member = (Member) request.getSession().getAttribute("logInInfo");
		String userId = "";
		
		if(member != null) {
			userId = member.getUserId();
		}
		String sessionId = request.getSession().getId();
		CartService cartService = new CartService();
		RequestDispatcher rd = null;
		
		System.out.println("sessionId : " + sessionId);
		
		List<Map<String,Object>> cartList = cartService.selectCart(userId, sessionId);
		
		if(cartList != null) {
			request.setAttribute("data", cartList);
			rd = request.getRequestDispatcher(prefix + "cart/cartList.jsp");
		}else {
			request.setAttribute("alertMsg", "장바구니에 등록한 도서가 없습니다.");
			request.setAttribute("back", "back");
			rd = request.getRequestDispatcher(prefix + "common/result.jsp");
		}
		
		rd.forward(request, response);
	}
	
	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		CartService cartService = new CartService();
		String bIdx = request.getParameter("bIdx");
		String sessionId = request.getSession().getId();
		
		Member member = (Member) request.getSession(false).getAttribute("logInInfo");
		String userId = "";
		
		if(member != null) {
			userId = member.getUserId();
		}
				
		cartService.insertCart(bIdx, userId, sessionId);
		response.sendRedirect(request.getContextPath() + "/cart/cart.do");
	}
	
	public void removeCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		CartService cartService = new CartService();
		String bcIdx = request.getParameter("bcIdx");
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("utf8");
		cartService.deleteCart(bcIdx);
		
		PrintWriter out = response.getWriter();
		
		//뒤에 \r\n 붙어서 날아간다...
		//out.println("success");
		out.print("success");
	}
}
