package com.uclass.bookmanager.mypage.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.common.db.JDBCTemplate;

public class MypageDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public int updateMember(Connection conn, Member member) throws SQLException {
		
		PreparedStatement pstm = null;
		int res = 0;
		String sql = "update tb_member set password = ?"
				+ ", email = ?"
				+ ", tell = ? "
				+ "where user_id = ?";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member.getPassword());
			pstm.setString(2, member.getEmail());
			pstm.setString(3, member.getTell());
			pstm.setString(4, member.getUserId());
			res = pstm.executeUpdate();
			
		}finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}
	
	public int updateMemberToLeave(Connection conn, String userId) throws SQLException {
		
		PreparedStatement pstm = null;
		int res = 0;
		
		String sql = "update tb_member set is_leave = 1 "
				+ "where user_id = ?";
	
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			res = pstm.executeUpdate();
			
		}finally {
			jdt.close(pstm);
		}
		
		return res;
		
	}

}
