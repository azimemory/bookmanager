package com.uclass.bookmanager.book.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.common.db.JDBCTemplate;

public class BookDao{
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();

	public List<Book> selectBookList(Connection conn, String keyword) throws SQLException{
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from tb_book where title like '%'||?||'%' or author like '%'||?||'%'";
		List<Book> result = null;
		
		try {
			result = new ArrayList<Book>();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, keyword);
			pstm.setString(2, keyword);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Book book = new Book();
				book.setbIdx(rs.getInt(1));
				book.setIsbn(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book.setAuthor(rs.getString(5));
				book.setInfo(rs.getString(6));
				book.setBookAmt(rs.getInt(7));
				book.setRegDate(rs.getDate(8));
				book.setRentCnt(rs.getInt(9));
				result.add(book);
			}
		} finally {
			jdt.close(rs, pstm);
		}
		
		return result;
	}
	
	public Book selectBook(Connection conn, String bIdx) throws SQLException{
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from tb_book where b_idx=?";
		Book book = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, bIdx);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				book = new Book();
				book.setbIdx(rs.getInt(1));
				book.setIsbn(rs.getString(2));
				book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book.setAuthor(rs.getString(5));
				book.setInfo(rs.getString(6));
				book.setBookAmt(rs.getInt(7));
				book.setRegDate(rs.getDate(8));
				book.setRentCnt(rs.getInt(9));
			}
		}finally {
			jdt.close(rs, pstm);
		}
		
		return book;
	}
	
	public int selectBookAmt(Connection conn, String bIdx) throws SQLException{
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select book_amt from tb_book where b_idx=?";
		int bookAmt = 0;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, bIdx);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				bookAmt = rs.getInt(1);
			}
		}finally {
			jdt.close(rs, pstm);
		}
		
		return bookAmt;
	}

}
