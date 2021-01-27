package com.uclass.bookmanager.mypage.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.mypage.model.dao.MypageDao;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.exception.CustomException;

public class MypageService {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public void updateMember(Member member) throws CustomException {
		MypageDao mypageDao = new MypageDao();
		Connection conn = jdt.getConnection();
	
		try {
			mypageDao.updateMember(conn, member);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.UM01,e);
		}finally {
			jdt.close(conn);
		}
	}
	
	public void updateMemberToLeave(String userId) throws CustomException {
		MypageDao mypageDao = new MypageDao();
		Connection conn = jdt.getConnection();
		
		try {
			mypageDao.updateMemberToLeave(conn, userId);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.UM01,e);
		}finally {
			jdt.close(conn);
		}
	}
}
