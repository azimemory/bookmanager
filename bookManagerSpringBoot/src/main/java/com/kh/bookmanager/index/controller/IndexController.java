package com.kh.bookmanager.index.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.index.model.service.IndexService;

@Controller
@RequestMapping("index/")
public class IndexController{

	private final IndexService indexService;
	
	@Autowired
	public IndexController(IndexService indexService) {
		this.indexService = indexService;
	}

	@RequestMapping("index.do")
	public String index(Model model, HttpServletRequest request){
		
		Map<String,List<Book>> books = indexService.selectBooksForIndex();
		//장바구니를 위한 session 생성
		HttpSession session = request.getSession();
		model.addAttribute("books", books);
		return "/index/index";
	}
}
