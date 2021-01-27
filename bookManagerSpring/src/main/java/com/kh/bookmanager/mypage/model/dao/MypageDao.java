package com.kh.bookmanager.mypage.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.bookmanager.member.model.vo.Member;

@Repository
public class MypageDao {
	
	@Autowired
	SqlSessionTemplate session;
	
	public int updateMember(Member member) {
		return session.update("MEMBER.updateMember", member);
	}
	
	public int updateMemberToLeave(String userId) {
		return session.update("MEMBER.leaveMember", userId);
	}

}
