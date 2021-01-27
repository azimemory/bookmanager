package com.kh.bookmanager.mypage.model.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.kh.bookmanager.member.model.vo.Member;
import com.kh.bookmanager.mypage.model.dao.MypageDao;

import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;

@Service
public class MypageServiceImpl implements MypageService{
	
	@Autowired
	MypageDao mypageDao;
	
	//@Autowired
	JavaMailSender mailSender;
	
	public void updateMember(Member member)  {
		if(mypageDao.updateMember(member) == 0) {
			throw new CustomException(ErrorCode.UM01);
		}
	}
	
	public void updateMemberToLeave(String userId)   {
		if( mypageDao.updateMemberToLeave(userId) == 0) {
			throw new CustomException(ErrorCode.UM01);
		}
	}
	
	public void mailSendingToLeave(Member member){
		
		String setfrom = "azimemory@naver.com";
		String tomail = member.getEmail();
		String title = "안녕히 가세요.";
		String htmlBody = "<h1>안녕히 가세요.</h1>";
		
		mailSender.send(new MimeMessagePreparator() {
			   public void prepare(MimeMessage mimeMessage) throws MessagingException {
			     MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			     //보내는 이
			     message.setFrom(setfrom);
			     //받는 이
			     message.setTo(tomail);
			     //메일 제목
			     message.setSubject(title);
			     //메일 내용
			     //두번째 boolean값은 html 여부 (true : html , false : text)
			     message.setText(htmlBody, true);
			   };
		});
	}
}
