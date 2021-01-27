package com.kh.bookmanager.index.model.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.bookmanager.book.model.vo.Book;

@Repository
public class IndexDao {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<Book> selectRecentPopular(){
		return sqlSession.selectList("INDEX.selectRecentPopular");
	}
	
	public List<Book> selectSteadyBook(){
		return sqlSession.selectList("INDEX.selectSteadyBook");
	}

	public List<Book> selectRecentInput(){
		return sqlSession.selectList("INDEX.selectRecentInput");
	}
	
	public List<Book> selectRecentRent(){
		return sqlSession.selectList("INDEX.selectRecentRent");
	}

}
