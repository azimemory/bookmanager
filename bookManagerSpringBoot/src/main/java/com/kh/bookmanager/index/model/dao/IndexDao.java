package com.kh.bookmanager.index.model.dao;


import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.book.model.vo.QBook;
import com.kh.bookmanager.rent.model.vo.QRentBook;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class IndexDao {
	
	JPAQueryFactory query;
	
	@Autowired
	public IndexDao(JPAQueryFactory query) {
		this.query = query;
	}
	
	public List<Book> selectRecentPopular(){
		
		QBook b = QBook.book;
		QRentBook rb = QRentBook.rentBook;
		long beforDate = System.currentTimeMillis() - 1000 * 60 * 60 *24 * 60;
		
		List<Book> result =  
			query.select(Projections.bean(Book.class,b.bIdx,b.title,b.author,b.isbn))
			 .from(b)
			 .innerJoin(rb).on(b.bIdx.eq(rb.bIdx))
			 .where(rb.regDate.after(new Date(beforDate)))
			 .groupBy(b.bIdx,b.title, b.author, b.isbn)
			 .orderBy(rb.count().desc())
			 .limit(5).fetch();
		
		/* 
		  select * 
			from
				(select rownum rnum, a.* 
				from
					(select 
			 			b.b_Idx, b.title, b.author, b.isbn, count(b.b_Idx) as cnt
					 from tb_book b
			 		 inner join tb_rent_book rb 
					 on(b.b_idx = rb.b_idx)
					 where rb.reg_date > sysdate - 60
					 group by b.b_Idx, b.title, b.author, b.isbn
					 order by cnt desc
					 ) a
			     ) where rnum < 6  
		*/
		
		return result;
	}
	
	public List<Book> selectSteadyBook(){
		
		QBook b = QBook.book;
		
		List<Book> result =  
				query.select(Projections.bean(Book.class,b.bIdx, b.title, b.author, b.isbn, b.regDate))
				.from(b)
				.orderBy(b.rentCnt.desc())
				.limit(5).fetch();
		/*
		  select *     
			from 
				(select rownum rnum, a.* 
				from
				(select b.b_Idx, b.title, b.author, b.isbn, b.reg_date     
			    	from tb_book b     
			    	order by b.rent_cnt desc
		    	) a   
			) where rnum < 6
		 */
		
		return result;
	}
	
	public List<Book> selectRecentInput(){
		
		QBook b = QBook.book;
		
		List<Book> result = 
				query.select(Projections.bean(Book.class, b.bIdx, b.title, b.author, b.isbn))
				.from(b)
				.orderBy(b.regDate.desc())
				.limit(5)
				.fetch();
		/*
		   select *     
			from 
			(select rownum rnum, a.* 
				from
				(select b.b_Idx, b.title, b.author, b.isbn     
			    	from tb_book b     
			    	order by b.reg_date desc
		    	) a  
			) where rnum < 6
		 */
		
		return result;
	}
	
	public List<Book> selectRecentRent(){
		
		QBook b = QBook.book;
		QRentBook rb = QRentBook.rentBook;
		
		List<Book> result =
				query.select(Projections.bean(Book.class, b.bIdx, b.title, b.author, b.isbn))
					.from(b)
					.innerJoin(rb).on(b.bIdx.eq(rb.bIdx))
					.orderBy(rb.regDate.desc())
					.limit(5).fetch();
		/*
		 	select *   
				from 
				(select rownum rnum, a.* 
					from
					(select b.b_Idx, b.title, b.author, b.isbn, rb.reg_date     
				        from tb_book b 
				        inner join tb_rent_book rb    
			            	on(b.b_idx = rb.b_idx)    
				        order by rb.reg_date desc ) a   
				) where rnum < 6
		 */
		
		return result;
	}
}
