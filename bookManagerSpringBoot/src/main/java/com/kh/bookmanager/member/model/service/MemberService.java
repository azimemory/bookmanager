package com.kh.bookmanager.member.model.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.kh.bookmanager.member.model.vo.Member;
import com.kh.bookmanager.common.exception.CustomException;

public interface MemberService extends UserDetailsService{

	public void insertMember(Member member) ;
	
	public void selectId(String userId) ;
	
	public int selectRentCount(String userId);
	
	public void mailSending(Member member, String urlPath);
}





