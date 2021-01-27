package com.uclass.bookmanager.index.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.bookmanager.common.db.JDBCTemplate;

public class IndexDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public List<Book> selectRecentPopular(Connection conn) throws SQLException {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		Book book = null;
		
		String sql = "select * from" + 
				"		(select rownum rnum, a.* from" + 
				"		(select " + 
				"			 b.b_Idx, b.title, b.author, b.isbn, count(b.b_Idx) as cnt" + 
				"		from tb_book b" + 
				"		inner join tb_rent_book rb " + 
				"		on(b.b_idx = rb.b_idx)" + 
				"		where rb.reg_date > sysdate - 60" + 
				"		group by b.b_Idx, b.title, b.author, b.isbn" + 
				"		order by cnt desc) a) where rnum < 6";
	
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				book = new Book();
				book.setbIdx(rs.getInt(2));
				book.setTitle(rs.getString(3));
				book.setAuthor(rs.getString(4));
				book.setIsbn(rs.getString(5));
				
				bookList.add(book);
			}
		}finally {
			jdt.close(rs,pstm);
		}
		
		return bookList;
	}
	
	public List<Book> selectSteadyBook(Connection conn) throws SQLException {
			
			PreparedStatement pstm = null;
			ResultSet rs = null;
			List<Book> bookList = new ArrayList<Book>();
			Book book = null;
			
			String sql = "select   " + 
					"*   " + 
					"from (select rownum rnum, a.* from(" + 
					"    select   " + 
					"        b.b_Idx, b.title, b.author, b.isbn, b.reg_date   " + 
					"    from  " + 
					"        tb_book b   " + 
					"    order by b.rent_cnt desc) a " + 
					") where rnum < 6";
			
			try {
				pstm = conn.prepareStatement(sql);
				rs = pstm.executeQuery();
				
				while(rs.next()) {
					book = new Book();
					book.setbIdx(rs.getInt(2));
					book.setTitle(rs.getString(3));
					book.setAuthor(rs.getString(4));
					book.setIsbn(rs.getString(5));
					
					bookList.add(book);
				}
			}finally {
				jdt.close(rs,pstm);
			}
			
			return bookList;
		}

	public List<Book> selectRecentInput(Connection conn) throws SQLException {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		Book book = null;
		
		String sql = "select   " + 
				"*   " + 
				"from (select rownum rnum, a.* from(" + 
				"    select   " + 
				"      b.b_Idx, b.title, b.author, b.isbn   " + 
				"    from  " + 
				"        tb_book b   " + 
				"    order by b.reg_date desc  ) a" + 
				") where rnum < 6";
	
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				book = new Book();
				book.setbIdx(rs.getInt(2));
				book.setTitle(rs.getString(3));
				book.setAuthor(rs.getString(4));
				book.setIsbn(rs.getString(5));
				
				bookList.add(book);
			}
		}finally {
			jdt.close(rs,pstm);
		}
		
		return bookList;
	}
	
	public List<Book> selectRecentRent(Connection conn) throws SQLException {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		Book book = null;
		
		String sql = "select  " + 
				" * " + 
				"from (select rownum rnum, a.* from(  " + 
				"        select   " + 
				"            b.b_Idx, b.title, b.author, b.isbn, rb.reg_date   " + 
				"        from  " + 
				"            tb_book b inner join tb_rent_book rb  " + 
				"            on(b.b_idx = rb.b_idx)  " + 
				"        order by rb.reg_date desc ) a " + 
				") where rnum < 6";
	
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				book = new Book();
				book.setbIdx(rs.getInt(2));
				book.setTitle(rs.getString(3));
				book.setAuthor(rs.getString(4));
				book.setIsbn(rs.getString(5));
				
				bookList.add(book);
			}
		}finally {
			jdt.close(rs,pstm);
		}
		
		return bookList;
	}
	

	

}
