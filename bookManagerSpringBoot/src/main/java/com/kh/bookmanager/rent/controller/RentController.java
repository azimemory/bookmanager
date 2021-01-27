package com.kh.bookmanager.rent.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.member.model.vo.Member;
import com.kh.bookmanager.rent.model.service.RentBookService;
import com.kh.bookmanager.rent.model.vo.Rent;
import com.kh.bookmanager.rent.model.vo.RentBook;

import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;

@Controller
@RequestMapping("rent/")	
public class RentController{

	private final RentBookService rentBookService;
	
	@Autowired
	public RentController(RentBookService rentBookService) {
		this.rentBookService = rentBookService;
	}

	@RequestMapping("rent.do")	
	public String rentBook(Model model, HttpSession session, Book book) {
		
		Member member = (Member) session.getAttribute("logInInfo");
		int rentCnt = member.getRentCnt();
		rentCnt++;
		
		if(member.getRentableDate().after(new Date())) {
			model.addAttribute("alertMsg", member.getRentableDate() + "이후에 대출이 가능합니다.");
			model.addAttribute("back", "back");
			return "/common/result";
		}
		
		if(rentCnt > 5 ) {
			System.out.println(rentCnt);
			model.addAttribute("alertMsg", "도서는 5권까지만 대출 가능합니다.");
			model.addAttribute("back", "back");
			return "/common/result";
		}
			
		Rent rent = new Rent();
		rent.setUserId(member.getUserId());
		rent.setBook(book);
		rent.setRentBookCnt(1);
		rent.setTitle(book.getTitle());
		
		rentBookService.insertRentBookInfo(rent);
		
		member.setRentCnt(rentCnt);
		model.addAttribute("alertMsg", "도서대출에 성공했습니다. 대출번호는 " + rent.getRmIdx() + " 입니다.");
		model.addAttribute("url", "/rent/list.do");
		
		return "common/result";
	}
	
	//produces :  어떠한 데이터 형식으로 응답할 것인가를 결정
	//consumes :  어떠한 요청에 대해서 처리할 것인가를 결정
	@RequestMapping(value="multi.do", produces="text/plain;charset=UTF-8")	
	@ResponseBody
	public String rentBooks(Model model, 
			//@RequestBody : reuqest message의 body에 있는 데이터를 컨트롤러의 변수에 매핑해준다.
			//json형태의 데이터를 받아서 사용
							@RequestBody Map<String,List<String>> jsonData, 
							HttpSession session ){
				
		List<Book> bookList = new ArrayList<Book>();
		Member member = (Member)session.getAttribute("logInInfo");
		int rentCnt = member.getRentCnt();
		System.out.println("jsonData : " + jsonData );
		int i = 0;
		
		if(jsonData.get("bIdxs").size() == 0) {
			throw new CustomException(ErrorCode.IR07);
		}
		
		for(String bIdx : jsonData.get("bIdxs")) {
			Book book = new Book();
			book.setbIdx(bIdx);
			book.setTitle(jsonData.get("titles").get(i++));
			bookList.add(book);
		}
		
		rentCnt += bookList.size();
		
		if(rentCnt > 5 ) {
			throw new CustomException(ErrorCode.IR06);
		}else {
			
			String title = bookList.get(0).getTitle() + " 외 " + bookList.size() + "권";
			
			Rent rent = new Rent();
			rent.setUserId(member.getUserId());
			rent.setTitle(title);
			rent.setRentBookList(bookList);
			rent.setRentBookCnt(bookList.size());
			
			rentBookService.insertRentBooksInfo(rent);
			member.setRentCnt(member.getRentCnt()+1);
			return "대출이 완료되었습니다. 대출번호는 " + rent.getRmIdx() + " 입니다.";
		}
	}
	
	@RequestMapping("return.do")	
	public String returnBook(Model model, Rent rent, HttpSession session) {
		
		Member member = (Member)session.getAttribute("logInInfo");
		rent.setUserId(member.getUserId());
		
		rentBookService.updateReturnRentBook(rent);
		member.setRentCnt(member.getRentCnt()-1);
		model.addAttribute("alertMsg", "대출도서번호 " + rent.getRbIdx() + "건 반납처리가 완료되었습니다.");
		model.addAttribute("url", "/rent/list.do");
		
		return "common/result";
	}
	
	@RequestMapping("extend.do")	
	public String extendBook(Model model, Rent rent, HttpSession session) {

		Member member = (Member)session.getAttribute("logInInfo");
		String memberGrade = member.getGrade();
		
		rentBookService.updateExtendRentBook(rent, memberGrade);
		
		model.addAttribute("alertMsg", "대출도서번호 " + rent.getRbIdx() + "번 연장처리가 완료되었습니다.");
		model.addAttribute("url", "/rent/list.do");
		
		return "common/result";
	}
	
	@RequestMapping("list.do")	
	public String rentList(Model model
						, @RequestParam(required = false, defaultValue = "1") int cPage
					     , HttpSession session) {
		
		Member member = (Member)session.getAttribute("logInInfo");
		int cntPerPage = 5;
		
		Map<String,Object> commandMap = 
				rentBookService.selectRentinfo(member.getUserId(),cPage,cntPerPage);
		
		model.addAttribute("data", commandMap.get("data"));
		model.addAttribute("paging", commandMap.get("paging"));
		
		return "mypage/rentList";
	}
	
	@RequestMapping("detail.do")	
	public String rentDetail(Model model, int rmIdx) {
		
		Map<String,Object> commandMap = new HashMap<String, Object>();
		commandMap.put("searchType","rmIdx");
		commandMap.put("rmIdx",rmIdx);
		
		List<RentBook> rentBookList = rentBookService.selectRentinfo(commandMap);
		model.addAttribute("data", rentBookList);
		return "mypage/rentDetail";
	}
}
