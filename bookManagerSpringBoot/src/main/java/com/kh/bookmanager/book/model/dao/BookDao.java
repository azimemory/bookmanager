package com.kh.bookmanager.book.model.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.book.model.vo.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BookDao{
	
	@Autowired
	JPAQueryFactory query;
	QBook b = QBook.book;
	
	public List<Book> selectBookList(String keyword){
		List<Book> result = 
				query.select(b).from(b)
				.where(b.title.contains(keyword).or(b.author.contains(keyword)))
				.fetch();
					
		return result;
	}
	
	public Book selectBook(int bIdx){
		return query.select(b).from(b).where(b.bIdx.eq(bIdx)).fetchOne();
	}
	
	public List<Book> selectInnerSearch(Map<String, Object> commandMap){
		
		//도서대출목록 반환
		int[] bIdxs = (int[])commandMap.get("bIdxs");
		//검색할 키워드
		String keyword = (String)commandMap.get("keyword");
		//정렬할 키워드
		String orderBy = (String)commandMap.get("orderBy");
		
		//BooleanBuilder로 동적 조건절 생성
		BooleanBuilder builder = new BooleanBuilder();
		//builder.and(b.bIdx.in(bIdxs));
		if(keyword != null) {
			builder.and(b.title.contains(keyword)).or(b.author.contains(keyword));
		}
		
		//StringPath를 생성해 동적으로 orderPath 지정
		StringPath orderPath = Expressions.stringPath("bIdx");
		if(orderBy != null) {
			orderPath = Expressions.stringPath(orderBy);
		}
		
		return query.select(b).from(b).where(builder).orderBy(orderPath.desc()).fetch();
	}
	
	public int selectBookAmt(int i){
		return (int)query.select(b).from(b).where(b.bIdx.eq(i)).fetchCount();
	}
	
}
