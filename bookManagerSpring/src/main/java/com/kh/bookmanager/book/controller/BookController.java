package com.kh.bookmanager.book.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.bookmanager.book.model.service.BookService;
import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;

@Controller
@RequestMapping("book/")
public class BookController{
	
	private final BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping("search.do")
	public String search(Model model, String keyword) {
		List<Book> bookList = bookService.selectBookList(keyword);
		model.addAttribute("bookList", bookList);
		return "/book/bookSearch";
	}
	
	@RequestMapping("detail.do")
	public String detail(Model model,int bIdx) {
		Book book = bookService.selectBook(bIdx);
		model.addAttribute("book", book);
		return "/book/bookDetail";
	}
	
	@RequestMapping("innersearch.do")
	public ModelAndView innerSearchBook(String[] bIdxs, String keyword, String orderBy) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		Map<String, Object> commandMap = new HashMap<String, Object>();
		
		if(bIdxs == null) {
			throw new CustomException(ErrorCode.SB02);
		}
		
		commandMap.put("bIdxs",bIdxs);
		commandMap.put("keyword",keyword);
		commandMap.put("orderBy",orderBy);
		
		List<Book> res = bookService.selectInnerSearch(commandMap);
		mav.addObject("bookList", res);
		mav.setViewName("/book/bookSearch");
		
		return mav;
	}
}
