package com.kh.bookmanager.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.bookmanager.member.model.service.MemberService;
import com.kh.bookmanager.member.model.vo.Member;

import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.exception.CustomException;

//MemberController를 bean으로 등록

//Controller
//1. 사용자의 요청을 받아
//   어플리케이션에 알맞은 형태로 데이터를 파싱
//   적절한 Service의 메서드를 호출
//   Service로 부터 받은 결과값을 사용자에게 반환
  @Controller
  @RequestMapping("member/")
  public class MemberController {
	  
	  private final MemberService memberService;
	  
	  @Autowired   
	  public MemberController(MemberService memberService) {
		  this.memberService = memberService;
	  }
	  
	/*  
	 *  @RequestMapping(value="/member/login.do")
	 	//해당 메서드를 어떤 url에 매핑 시킬 지 설정
	 	//value 속성을 사용해서 url 지정할 수 있다.
	 	//void : 요청이 온 url과 같은 경로의 jsp를 호출
		 public void logIn() {
		  	System.out.println("로그인 메서드 호출되었습니다.");
		 }
	 */
	  
	  //@RequestMapping("/login.do")
	  //RequestMapping에서 사용할 속성이 value밖에 없으면
	  //value 생략할 수 있다.
	  //class에 적용 : 모든 메서드에 적용되는 url경로 앞에 붙을 경로를 지정
	  //ModelAndView 객체의 setViewName 메서드의 파라미터로
	  //원하는 jsp경로를 입력해 jsp를 호출 할 수 있다.
	
	  /*public ModelAndView logIn() {
		  ModelAndView mav = new ModelAndView();
		  mav.setViewName("member/login");
		  System.out.println("로그인 메서드 호출되었습니다.");
		  return mav;
	    }*/
	
	@RequestMapping(value="login.do"
			, method=RequestMethod.GET)
	//@RequestMapping의 method 속성에 http method를 지정할 수 있다.
	//반환형이 String이라면 String의 값으로 jsp를 찾는다.
	public String login() {
		//System.out.println("login 메서드 호출");
		//절대경로로 잡으면 contextPath 아래부터 경로가 잡힌다.
		return "/member/login";
	}
	
	@RequestMapping("logout.do")
	public String logout(
			HttpSession session
			) {
		session.removeAttribute("logInInfo");
		//redirect도 마찬가지로 contextPath아래부터 잡힌다.
		return "redirect:/member/login.do";
	}
  
	@RequestMapping("join.do")
	public void join() {}
	
	//메소드에 @ResponseBody 어노테이션을 지정하면
	//메서드에서 리턴하는 값을 viewResolver를 거쳐서 출력하지 않고
	//Http Response Body에 직접 쓰게 된다.
	@RequestMapping(value="/idcheck.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String idCheck(@RequestBody Map<String,String> jsonData) {
		memberService.selectId(jsonData.get("userId"));
		return "사용 가능한 아이디 입니다.";
	}
	
	@RequestMapping("joinemailcheck.do")
	public ModelAndView joinEmailCheck(
			Member member
			,HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String urlPath = request.getServerName()
				+":"+request.getServerPort()
				+request.getContextPath();
		
		memberService.mailSending(member, urlPath);
		
		mav.addObject("alertMsg", "이메일로 확인 메일이 발송 되었습니다.");
		mav.addObject("url","/index/index.do");
		mav.setViewName("common/result");
		
		return mav;
	}
	
	@RequestMapping("joinimpl.do")
	public ModelAndView joinImpl(@ModelAttribute Member member,
			HttpServletRequest request)  {
		
		//ContextPath경로값을 받아온다.
		ModelAndView mav = new ModelAndView();
		
		memberService.insertMember(member);
		mav.addObject("alertMsg", "회원가입에 성공하였습니다.");
		mav.addObject("url", "/member/login.do");
		mav.setViewName("/common/result");
		
		return mav;
	}
  }
 
 
