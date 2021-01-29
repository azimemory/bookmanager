package com.uclass.bookmanager.book.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.uclass.bookmanager.book.model.dao.BookDao;
import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.exception.CustomException;

public class BookService{
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public List<Book> selectBookList(String keyword)  {
		
		BookDao bookDao = new BookDao();
		Connection conn = jdt.getConnection();
		List<Book> result = null;
		
		try {
			result = bookDao.selectBookList(conn, keyword);
		} catch (SQLException e) {
			throw new CustomException(ErrorCode.SB01, e);
		}finally {
			jdt.close(conn);
		}
		return result;
	}
	
	public Book selectBook(String bIdx)  {
		
		BookDao bookDao = new BookDao();
		Connection conn = jdt.getConnection();
		Book book = null;
		
		try {
			book = bookDao.selectBook(conn, bIdx);
		} catch (SQLException e) {
			throw new CustomException(ErrorCode.SB01, e);
		}finally {
			jdt.close(conn);
		}
		
		return book;
	}
}
