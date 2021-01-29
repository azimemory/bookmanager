package com.kh.bookmanager.mypage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.bookmanager.member.model.vo.Member;
import com.kh.bookmanager.mypage.model.service.MypageService;
import com.kh.common.exception.CustomException;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	private final MypageService mypageService;
	
	@Autowired
	public MypageController(MypageService mypageService) {
		this.mypageService = mypageService;
	}
	
	@RequestMapping("/mypage.do")
	public void mypage() {}
	
	@RequestMapping("/modify.do")
	public String modify(
			//@ModelAttribute 어노테이션은 생략 가능
			 Member member
			,HttpSession session
			//ModelAndView에서 값을 저장하는 Model객체
			//view 경로를 지정하는 것은 return하는 String에 지정한다.
			,Model model
			)  {
		
		Member sessionMember
			= (Member)session.getAttribute("logInInfo");
		member.setUserId(sessionMember.getUserId());
		
		mypageService.updateMember(member);
		
		//회원 수정에 성공했다면
		//Model.addAttribute(K,V) 
		//: view에 전달할 데이터를 추가하는 메서드
		model.addAttribute("alertMsg","회원정보 수정에 성공했습니다.");
		model.addAttribute("url", "/mypage/mypage.do");
		sessionMember.setPassword(member.getPassword());
		sessionMember.setEmail(member.getEmail());
		sessionMember.setTell(member.getTell());
		
		return "common/result";
	}
	
	@RequestMapping("/leave.do")
	public String memberLeave(
			HttpSession session
			,Model model)  {
		
		Member member = (Member)session.getAttribute("logInInfo");
		mypageService.updateMemberToLeave(member.getUserId());
		mypageService.mailSendingToLeave(member);
		session.removeAttribute("logInInfo");
		
		model.addAttribute("alertMsg", "회원탈퇴하셨습니다.");
		model.addAttribute("url", "/member/login.do");
		return "common/result";
	}
}
