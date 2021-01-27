package com.uclass.bookmanager.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.common.db.JDBCTemplate;

public class MemberDao{
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();

	public Member selectMember(Connection conn, String userId, String password) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Member member = null;
		String sql = "select * from tb_member where user_id = ? and password = ?";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			pstm.setString(2, password);
			rs = pstm.executeQuery();

			if(rs.next()) {
				member = new Member();
				member.setUserId(rs.getString(1));
				member.setPassword(rs.getString(2));
				member.setEmail(rs.getString(3));
				member.setGrade(rs.getString(4));
				member.setTell(rs.getString(5));
				member.setRegDate(rs.getDate(6));
				member.setRentableDate(rs.getDate(7));
				member.setIsLeave(rs.getInt(8));
			}
		} finally {
			jdt.close(rs,pstm);
		}
		
		return member;
	}
	
	public int selectRentCount(Connection conn, String userId) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int res = 0;
		String sql = " select count(*) "
				+ " from tb_rent_book "
				+ " where user_id = ? "
				+ " and (state = 'RE00' or state = 'RE01')";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();

			if(rs.next()) {
				res = rs.getInt(1);
			}
		}finally {
			jdt.close(rs,pstm);
		}
		
		return res;
	}
	
	public int checkId(Connection conn, String userId) throws SQLException{
		String sql = "select count(*) from tb_member where user_id = '" + userId + "'";
		Statement stmt = null;
		ResultSet rs = null;
		int res = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				res =  rs.getInt(1);
			}
			return res;
		} finally {
			jdt.close(rs,stmt);
		}
	}
	
	public boolean insertMember(Connection con, Member member) throws SQLException{
		boolean isSuccess = true;
		String sql = "insert into tb_member"
				+ "(user_id, password ,tell, email) "
				+ "values(?,?,?,?)"; 
		
		PreparedStatement pstm = null;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, member.getUserId());
			pstm.setString(2, member.getPassword());
			pstm.setString(3, member.getTell());
			pstm.setString(4, member.getEmail());
			if(pstm.executeUpdate() == 0) 
				isSuccess = false;
		}finally {
			jdt.close(pstm);
		}
		
		return isSuccess;
	}

	public boolean updateMemberInfo(Connection con, Member member) throws SQLException{
		boolean isSuccess = true;
		String sql = "update tb_member set " 
		+ " password = ? "
		+ ", tell = ? "
		+ ", email = ? "
		+ " where user_id = ? ";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getTell());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getUserId());
			if(pstmt.executeUpdate() == 0) 
				isSuccess = false;
		}finally {
			jdt.close(pstmt);
		}
		
		return isSuccess;
	}
	
	public int updateMemberCart(Connection con, String userId, String sessionId) throws SQLException{
		
		int res = 0;
		
		String sql = "update tb_book_cart set " 
		+ " user_id = ? "
		+ " where session_id = ? ";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, sessionId);
			res = pstmt.executeUpdate();
			
		}finally {
			jdt.close(pstmt);
		}
		
		return res;
	}
	
}
