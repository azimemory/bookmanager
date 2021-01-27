package com.kh.bookmanager.mypage.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.bookmanager.member.model.vo.Member;
import com.kh.bookmanager.member.model.vo.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class MypageDao {
	
	@Autowired
	JPAQueryFactory query;
	QMember m = QMember.member;
	public int updateMember(Member member) {
		return (int) query.update(m)
				.set(m.password,member.getPassword())
				.set(m.email, member.getEmail())
				.set(m.tell, member.getTell())
				.where(m.userId.eq(member.getUserId())).execute();
	}
	
	public int updateMemberToLeave(String userId) {
		return (int) query.update(m)
				.set(m.isLeave, 1)
				.where(m.userId.eq(userId))
				.execute();
	}
}
