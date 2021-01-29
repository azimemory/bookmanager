package com.kh.bookmanager.member.model.service;

import java.util.Map;

import com.kh.bookmanager.member.model.vo.Member;
import com.kh.common.exception.CustomException;

public interface MemberService {

	public void insertMember(Member member) ;
	
	public Member selectMember(Map<String,Object> commandMap) ;
	
	public void selectId(String userId) ;
	
	public int selectRentCount(String userId);
	
	public void mailSending(Member member, String urlPath);
	
}





