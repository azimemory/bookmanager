package com.kh.bookmanager.index.model.service;

import java.util.List;
import java.util.Map;

import com.kh.bookmanager.book.model.vo.Book;

public interface IndexService {
	
	public Map<String,List<Book>> selectBooksForIndex();

}
