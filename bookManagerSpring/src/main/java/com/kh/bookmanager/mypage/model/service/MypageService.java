package com.kh.bookmanager.mypage.model.service;

import com.kh.bookmanager.member.model.vo.Member;
import com.kh.common.exception.CustomException;

public interface MypageService {

	public void updateMember(Member member) ;
	
	public void updateMemberToLeave(String userId) ;
	
	public void mailSendingToLeave(Member member);
	
}
