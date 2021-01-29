package com.uclass.bookmanager.board.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.uclass.bookmanager.board.model.service.BoardService;
import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.exception.CustomException;
import com.uclass.bookmanager.common.util.file.FileUtil;
import com.uclass.bookmanager.common.util.file.FileVo;

/**
 * Servlet implementation class BoardController
 */
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String PREFIX = "/WEB-INF/views/";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
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
		if(path.equals("write.do")) {
			boardForm(request,response);
		}else if(path.equals("upload.do")) {
			boardUpload(request,response);
		}else if(path.equals("list.do")) {
			boardList(request,response);
		}else if(path.equals("detail.do")) {
			boardDetail(request,response);
		}else if(path.equals("download.do")) {
			boardDownload(request,response);
		}else if(path.equals("modify.do")) {
			boardModify(request,response);
		}else if(path.equals("modifyimpl.do")) {
			boardModifyImpl(request,response);
		}else if(path.equals("delete.do")) {
			boardDelete(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void boardForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "board/boardForm.jsp");
		rd.forward(request, response);
	}
	
	//추후에 수정 될 메서드
	public void boardUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		BoardService boardService = new BoardService();
		
		//session에 담겨있는 사용자의 정보를 가져온다.
		HttpSession session = request.getSession();
		Member sessionMember = (Member)session.getAttribute("logInInfo");
		
		boardService.insertBoard(sessionMember.getUserId(),request);
		request.setAttribute("alertMsg", "게시글이 등록되었습니다.");
		request.setAttribute("url", "/board/list.do");
		
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
		rd.forward(request, response);
	}
	
	public void boardList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardService boardService = new BoardService();
		int cntPerPage = 10;
		int cPage = 1;
		
		if(request.getParameter("cPage") != null) {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}
		
		Map<String, Object> commandMap 
			= boardService.selectBoardList(cPage, cntPerPage);
		request.setAttribute("paging", commandMap.get("paging"));
		request.setAttribute("data", commandMap);
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "board/boardList.jsp");
		rd.forward(request, response);
		
	}
	
	public void boardDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardService boardService = new BoardService();
		Map<String,Object> commandMap = boardService.selectBoardDetail(request.getParameter("bdIdx"));
		RequestDispatcher rd = null;
		
		//해당 게시글이 존재하는 지 여부 판단
		//반환되는 Map은 null일 수 없다.
		//Map안의 Board객체가 null인지 여부로 판단.
		request.setAttribute("data", commandMap);
		rd = request.getRequestDispatcher(PREFIX + "board/boardView.jsp");
		
		rd.forward(request, response);
	}

	public void boardDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String originFileName = request.getParameter("ofname");
		String renameFileName = request.getParameter("rfname");
		String subPath = request.getParameter("savePath");
		
		response.setHeader("content-disposition", "attachment; filename="+URLEncoder.encode(originFileName,"utf-8"));
		request.getRequestDispatcher("/upload/"+subPath+renameFileName)
		.forward(request, response);
	}
	
	public void boardModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardService BoardService = new BoardService();
		String userId = request.getParameter("userId");

		HttpSession session = request.getSession();
		Member sessionMember = (Member)session.getAttribute("logInInfo");
		RequestDispatcher rd = null;
		
		//해당 게시글 작성자 아이디와
		//session에 담겨있는 회원 아이디가 일치하는 경우에만 수정을 허용
		if(!(userId.equals(sessionMember.getUserId()))) {
			throw new CustomException(ErrorCode.AM01);
		}
		
		Map<String, Object> commandMap = BoardService.selectBoardDetail(request.getParameter("bdIdx"));
		request.setAttribute("data", commandMap);
		rd = request.getRequestDispatcher(PREFIX + "board/boardModify.jsp");
		rd.forward(request, response);
	}
	
	
	public void boardModifyImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardService boardService = new BoardService();
		boardService.updateBoard(request);
		request.setAttribute("alertMsg", "게시글 수정이 완료되었습니다.");
		request.setAttribute("url", "/board/list.do");
		RequestDispatcher rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
		rd.forward(request, response);
		
	}
	
	public void boardDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	   BoardService boardService = new BoardService(); 
	   HttpSession session =  request.getSession(); 
	   Member sessionMember =  (Member)session.getAttribute("logInInfo");
	   RequestDispatcher rd = null;
	  
	   String userId = request.getParameter("userId");
	  
	   //해당 게시글 작성자 아이디와
	   //session에 담겨있는 회원 아이디가 일치하는 경우에만 수정을 허용
	   if(sessionMember == null || !(userId.equals(sessionMember.getUserId()))) {
		 throw new CustomException(ErrorCode.AM01);
	   }
	  
	   boardService.deleteBoard(request.getParameter("bdIdx")); 
	   request.setAttribute("alertMsg", "게시글 삭제가 완료되었습니다.");
	   request.setAttribute("url", "/board/list.do");
	   rd = request.getRequestDispatcher(PREFIX + "common/result.jsp");
	   rd.forward(request, response);
	}
}
