package com.kh.bookmanager.index.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.index.model.dao.IndexDao;

@Service
public class IndexServiceImpl implements IndexService{
	
	private final IndexDao indexDao;
	
	@Autowired
	public IndexServiceImpl(IndexDao indexDao) {
		this.indexDao = indexDao;
	}
	
	public Map<String,List<Book>> selectBooksForIndex() {
		
		Map<String,List<Book>> books = null;
		
		books = new HashMap<String, List<Book>>();
		books.put("recentPopular",indexDao.selectRecentPopular());
		books.put("steadyBook",indexDao.selectSteadyBook());
		books.put("recentInput",indexDao.selectRecentInput());
		books.put("recentRent",indexDao.selectRecentRent());
		
		return books;
	}
}
