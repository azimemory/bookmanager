package com.uclass.bookmanager.cart.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uclass.common.db.JDBCTemplate;

public class CartDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public List<Map<String,Object>> selectCart(Connection conn, String userId, String sessionId) throws SQLException {
		
		List<Map<String,Object>> cartList = new ArrayList<Map<String,Object>>();
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Map<String,Object> cart = null;
		String sql = "select " + 
				"b.title, b.author, b.isbn, b.book_Amt, bc.bc_idx, b_idx " + 
				" from " + 
				" tb_book b " + 
				" inner join tb_book_cart bc " + 
				" using(b_idx) " + 
				" where bc.user_id = ? or bc.session_id = ? ";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			pstm.setString(2, sessionId);
			rs = pstm.executeQuery();

			while(rs.next()) {
				cart = new HashMap<String, Object>();
				cart.put("title", rs.getString(1));
				cart.put("author", rs.getString(2));
				cart.put("isbn", rs.getString(3));
				cart.put("bookAmt", rs.getString(4));
				cart.put("bcIdx", rs.getString(5));
				cart.put("bIdx", rs.getString(6));
				cartList.add(cart);
			}
		} finally {
			jdt.close(rs,pstm);
		}
		
		
		return cartList;
	}
	
	public int insertCart(Connection conn, String bIdx, String userId, String sessionId) throws SQLException{
		
		int res = 0;
		
		PreparedStatement pstm = null;
		String sql = "insert into tb_book_cart" + 
				"(BC_IDX, USER_ID, B_IDX, REG_DATE, SESSION_ID)" + 
				" values(sc_bc_idx.nextval,?,?,sysdate,?) ";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			pstm.setString(2, bIdx);
			pstm.setString(3, sessionId);
			
			res = pstm.executeUpdate();

		}finally {
			jdt.close(pstm);
		}
		
		return res;
	}
	
	public int deleteCart(Connection conn, String bcIdx) throws SQLException{
		
		int res = 0;
		
		PreparedStatement pstm = null;
		String sql = "delete tb_book_cart where bc_idx = ?";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, bcIdx);
			res = pstm.executeUpdate();
		}finally {
			jdt.close(pstm);
		}
		
		return res;
	}

}
