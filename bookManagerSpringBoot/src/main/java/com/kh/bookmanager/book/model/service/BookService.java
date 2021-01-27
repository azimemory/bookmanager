package com.kh.bookmanager.book.model.service;

import java.util.List;
import java.util.Map;

import com.kh.bookmanager.book.model.vo.Book;

import com.kh.bookmanager.common.exception.CustomException;

public interface BookService {
	
	public List<Book> selectBookList(String keyword) ;
	
	public Book selectBook(int bIdx) ;
	
	public List<Book> selectInnerSearch(Map<String, Object> commandMap);

}
