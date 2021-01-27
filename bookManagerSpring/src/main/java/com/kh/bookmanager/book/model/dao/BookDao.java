package com.kh.bookmanager.book.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.bookmanager.book.model.vo.Book;

@Repository
public class BookDao{
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<Book> selectBookList(String keyword){
		return sqlSession.selectList("BOOK.selectBookList",keyword);
	}
	
	public Book selectBook(int bIdx){
		return sqlSession.selectOne("BOOK.selectBook",bIdx);
	}
	
	public List<Book> selectInnerSearch(Map<String, Object> commandMap){
		List<Book> res = sqlSession.selectList("BOOK.selectInnerSearch",commandMap);
		return res;
	}
	
	public int selectBookAmt(int bIdx){
		return sqlSession.selectOne("BOOK.selectBookAmt",bIdx);
	}
	
}
