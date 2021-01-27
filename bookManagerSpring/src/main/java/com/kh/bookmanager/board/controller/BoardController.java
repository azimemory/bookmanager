package com.kh.bookmanager.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.kh.bookmanager.board.model.service.BoardService;
import com.kh.bookmanager.board.model.vo.Board;
import com.kh.bookmanager.common.code.Code;
import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.member.model.vo.Member;

@Controller
public class BoardController {
	
	//상수로 service를 선언 한다. 
	private final BoardService boardService;
	
	//생성자로 상수를 초기와.
	//Spring의 singleton은 쓰래드세이프를 보장하지 않는다.
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	
	@RequestMapping("board/write.do")
	public String boardForm() {
		return "/board/boardForm";
	}
	
	//추후에 수정 될 메서드
	@RequestMapping("/board/upload.do")
	public ModelAndView boardUpload(
			//다중파일 업로드임으로
			//여러개의 MultipartFile을 담기 위한 List 생성
			@RequestParam List<MultipartFile> files
			, HttpSession session
			, Board Board
			)  {
			ModelAndView mav = new ModelAndView();
			
			Member sessionMember = (Member)session.getAttribute("logInInfo");
			
			//로그인한 회원이라면
			if(sessionMember != null) {
				//게시글 작성자에 해당 회원의 아이디
				Board.setUserId(sessionMember.getUserId());
			}else {
				//로그인한 회원이 아니라면 비회원으로 등록
				Board.setUserId("비회원");
			}
			
			boardService.insertBoard(Board, files);
			//Board/Boardlist.do로 다시 요청
			mav.setViewName("redirect:list.do");
			return mav;
	}
	
	@RequestMapping("board/list.do")
	public ModelAndView boardList(
			//@RequestParam 
			//required : 필수 파라미터 여부 지정(default 값은  true)
			//defaultValue : 파라미터가 없을 때 기본값으로 지정할 값
			@RequestParam(required=false, defaultValue="1") 
			int cPage){
			
		ModelAndView mav = new ModelAndView();
		int cntPerPage = 10;
		
		Map<String, Object> commandMap 
			= boardService.selectBoardList(cPage, cntPerPage);
		
		//paging객체를 paging이라는 키로 담아서 보낸다.
		mav.addObject("paging", commandMap.get("paging"));
		mav.addObject("data", commandMap);
		mav.setViewName("board/boardList");
		return mav;
	}
	
	@RequestMapping("/board/detail.do")
	public ModelAndView boardDetail(String bdIdx)  {
		ModelAndView mav = new ModelAndView();
		Map<String,Object> commandMap = boardService.selectBoardDetail(bdIdx);
		mav.addObject("data", commandMap);
		mav.setViewName("board/boardView");
		
		return mav;
	}
	
	@RequestMapping("board/download.do")
	@ResponseBody
	public FileSystemResource boardDownload(
			//response header 지정을 위한  response
			HttpServletResponse response,
			//사용자가 올린 파일 이름
			String ofname,
			//파일경로
			String savePath
			) {
		
		String readFolder = Code.UPLOAD_PATH.VALUE;				
		//FileSystemResource
		FileSystemResource downFile 
			= new FileSystemResource(readFolder + savePath);
		
		try {
			response.setHeader("Content-Disposition"
					, "attachment; filename="+
					URLEncoder.encode(ofname,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return downFile;
	}
	
	@RequestMapping("board/modify.do")
	public ModelAndView boardModify(
				 String bdIdx
				,String userId
				,HttpSession session
			)  {
		
		ModelAndView mav = new ModelAndView();
		
		Member sessionMember 
		= (Member)session.getAttribute("logInInfo");
		
		//해당 게시글 작성자 아이디와
		//session에 담겨있는 회원 아이디가 일치하는 경우에만 수정을 허용
		if(sessionMember != null && userId.equals(sessionMember.getUserId())) {
			Map<String, Object> commandMap = boardService.selectBoardDetail(bdIdx);
			mav.addObject("data", commandMap);
			mav.setViewName("board/boardModify");
		}else {
			throw new CustomException(ErrorCode.AM01);
		}
		
		return mav;
	}
	
	@RequestMapping("board/modifyimpl.do")
	public ModelAndView boardModifyImpl (
			 Board Board
			, @RequestParam List<MultipartFile> files
			, @RequestParam(required = false) List<String> rmFiles
			, HttpSession session
			)  {
		
		ModelAndView mav = new ModelAndView();
		Member sessionMember = (Member)session.getAttribute("logInInfo");
		
		//게시글 작성자가 수정요청을 한 것이 맞는 지 확인
		if(!(sessionMember.getUserId().equals(Board.getUserId()))) {
			throw new CustomException(ErrorCode.AM01);
		}
		
		if(rmFiles != null) {
			for(String fIdx : rmFiles) {
				boardService.deleteFileByFIdx(fIdx);
			}
		}
			
		boardService.updateBoard(Board,files);
		mav.addObject("alertMsg", "해당 게시물 수정에 성공하였습니다");
		mav.addObject("url", "/board/list.do");
		mav.setViewName("common/result");
		
		return mav;
	}
	
	@RequestMapping("board/delete.do")
	public ModelAndView boardDelete( String bdIdx
									, String userId
									,HttpSession session)  {
		
		ModelAndView mav = new ModelAndView();
		Member member = (Member)session.getAttribute("logInInfo");
		
		//게시글 작성자가 수정요청을 한 것이 맞는 지 확인
		if(!(member.getUserId().equals(userId))) {
			throw new CustomException(ErrorCode.AM01);
		}
		
		boardService.deleteBoard(bdIdx);
		mav.addObject("alertMsg", "게시물 삭제에 성공했습니다.");
		mav.addObject("url", "/board/list.do");
		mav.setViewName("common/result");
		
		return mav;
	}
}
