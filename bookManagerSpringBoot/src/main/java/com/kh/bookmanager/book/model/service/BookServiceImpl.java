package com.kh.bookmanager.book.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.bookmanager.book.model.dao.BookDao;
import com.kh.bookmanager.book.model.vo.Book;

import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;

@Service
public class BookServiceImpl implements BookService{
		
	private final BookDao bookDao;
	
	@Autowired
	public BookServiceImpl(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
	public List<Book> selectBookList(String keyword)  {
		
		List<Book> bookList = bookDao.selectBookList(keyword);
		
		if(bookList != null) {
			return bookList;
		}else {
			throw new CustomException(ErrorCode.SB01);
		}
	}
	
	public Book selectBook(int bIdx)  {
		
		Book book = bookDao.selectBook(bIdx);
		
		if(book != null) {
			return book;
		}else {
			throw new CustomException(ErrorCode.SB01);
		}
	}
	
	public List<Book> selectInnerSearch(Map<String, Object> commandMap){
		List<Book> res = bookDao.selectInnerSearch(commandMap);
		return res;
	}
}
