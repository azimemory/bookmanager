package com.uclass.bookmanager.cart.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.uclass.bookmanager.cart.model.dao.CartDao;
import com.uclass.common.code.ErrorCode;
import com.uclass.common.db.JDBCTemplate;
import com.uclass.common.exception.CustomException;

public class CartService {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public List<Map<String,Object>> selectCart(String userId, String sessionId)  {
		
		List<Map<String,Object>> cartList = null;
		Connection conn = null;
		CartDao cartDao = new CartDao();
		
		try {
			conn = jdt.getConnection();
			cartList = cartDao.selectCart(conn, userId, sessionId);
		} catch (SQLException e) {
			throw new CustomException(ErrorCode.SC01,e);
		}finally {
			jdt.close(conn);
		}
		
		return cartList;
	}
	
	public void insertCart(String bIdx, String userId, String sessionId)   {
		
		Connection conn = null;
		CartDao cartDao = new CartDao();
		List<Map<String,Object>> curCart = selectCart(userId, sessionId);
		
		for(Map<String,Object> cart: curCart ) {
			if(cart.get("bIdx").equals(bIdx)) {
				throw new CustomException(ErrorCode.SC02);
			}
		}
		
		try {
			conn = jdt.getConnection();
			if(cartDao.insertCart(conn, bIdx, userId, sessionId) == 0) {
				throw new CustomException(ErrorCode.IC01);
			};
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.SC01,e);
		}finally {
			jdt.close(conn);
		}
	}
	
	public void deleteCart(String bcIdx)  {
		Connection conn = null;
		CartDao cartDao = new CartDao();
		
		try {
			conn = jdt.getConnection();
			if(cartDao.deleteCart(conn, bcIdx) == 0) {
				throw new CustomException(ErrorCode.DC01);
			};
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.DC01,e);
		}finally {
			jdt.close(conn);
		}
	}
}
