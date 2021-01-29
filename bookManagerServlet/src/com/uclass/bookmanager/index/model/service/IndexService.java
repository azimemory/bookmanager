package com.uclass.bookmanager.index.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.bookmanager.index.model.dao.IndexDao;
import com.uclass.common.code.ErrorCode;
import com.uclass.common.db.JDBCTemplate;
import com.uclass.common.exception.CustomException;

public class IndexService {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public Map<String,List<Book>> selectBooksForIndex()  {
		
		IndexDao indexDao = new IndexDao();
		Connection conn = jdt.getConnection();
		Map<String,List<Book>> books = null;
		
		try {
			
			books = new HashMap<String, List<Book>>();
			books.put("recentPopular",indexDao.selectRecentPopular(conn));
			books.put("steadyBook",indexDao.selectSteadyBook(conn));
			books.put("recentInput",indexDao.selectRecentInput(conn));
			books.put("recentRent",indexDao.selectRecentRent(conn));
		
		} catch (SQLException e) {
			throw new CustomException(ErrorCode.SB01,e);
		}finally {
			jdt.close(conn);
		}
		
		return books;
	}
	
	

}
