package com.uclass.bookmanager.member.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.uclass.bookmanager.member.model.dao.MemberDao;
import com.uclass.bookmanager.member.model.vo.Member;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.exception.CustomException;
import com.uclass.bookmanager.common.exception.CustomException;

public class MemberService{
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public Member selectMember(String userId, String password, String sessionId){
		Connection conn = jdt.getConnection();
		Member member = null;
		MemberDao memberDao = new MemberDao();
		try {
			memberDao.updateMemberCart(conn, userId, sessionId);
			member = memberDao.selectMember(conn, userId, password);
			if(member == null) {
				throw new CustomException(ErrorCode.SM03);
			}
			
			if(member.getIsLeave() == 1) {
				throw new CustomException(ErrorCode.SM04);
			}
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.SM01,e);
		}finally {
			jdt.close(conn);
		}
		
		return member;
	}
	
	public int selectRentCount(String userId)  {
		Connection conn = jdt.getConnection();
		int res = 0;
		MemberDao memberDao = new MemberDao();
		try {
			res = memberDao.selectRentCount(conn, userId);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.SR01,e);
		}finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	public void selectId(String userId){
		Connection conn = jdt.getConnection();
		MemberDao memberDao = new MemberDao();
		
		try {
			if(memberDao.checkId(conn,userId) != 0) {
				throw new CustomException(ErrorCode.SM02);
			};
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.SM01,e);
		}finally {
			jdt.close(conn);
		}
	}
	
	public void insertMember(Member member) {
		Connection conn = jdt.getConnection();
		MemberDao memberDao = new MemberDao();
		
		try {
			memberDao.insertMember(conn, member);
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.IM01,e);
		}finally {
			jdt.close(conn);
		}
	}

	public void updateMemberInfo(Member member)  {
		Connection conn = jdt.getConnection();
		MemberDao memberDao = new MemberDao();
		
		try {
			memberDao.updateMemberInfo(conn, member);
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.UM01,e);
		}finally {
			jdt.close(conn);
		}
	}
	
}
