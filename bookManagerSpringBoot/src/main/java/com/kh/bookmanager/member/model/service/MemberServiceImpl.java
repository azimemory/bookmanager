package com.kh.bookmanager.member.model.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.bookmanager.member.model.dao.MemberDao;
import com.kh.bookmanager.member.model.vo.Member;

import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;

@Service
public class MemberServiceImpl implements MemberService{
	
	private final MemberDao memberDao;
	JavaMailSender mailSender;
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserDetails member = memberDao.selectMember(userId);
		if(member==null) {
            throw new UsernameNotFoundException(userId);
        }
		
		return member;
	}
	
	public void insertMember(Member member)  {
		
		//사용자가 입력한 password 
		String password = member.getPassword();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//password 암호화 , 매번 다른 방식으로 암호화가 진행
		password = passwordEncoder.encode(password);
		System.out.println("암호화 된 비밀번호 : " + password);
		
		member.setPassword(password);
		memberDao.insertMember(member);
	}
	
	public void selectId(String userId) {
		System.out.println(memberDao.selectId(userId));
		if(memberDao.selectId(userId) != 0) {
			throw new CustomException(ErrorCode.SM02);
		};
	}
	
	public int selectRentCount(String userId) {
		int	res = memberDao.selectRentCount(userId);
		return res;
	}
	
	public void mailSending(Member member, String urlPath){
		
		String setfrom = "azimemory@naver.com";
		String tomail = member.getEmail();
		String title = "회원가입을 환영합니다.";
		String htmlBody=
			"<form "
			+ "action='http://"+urlPath+"/member/joinimple.do'"
			+" method='post'>"
			+ "<h3>회원가입을 환영합니다</h3>"
			+ "<input type='hidden' value='" 
					+ member.getUserId() 
					+ "' name='userId'>"
			+ "<input type='hidden' value='"
					+ member.getPassword()
					+"' name='password'>"
			+ "<input type='hidden' value='"
					+ member.getTell()
					+"' name='tell'>"
			+ "<input type='hidden' value='"
					+ member.getEmail()
					+"' name='email'>"
			+ "<button type='submit'>전송하기</button></form>";
		
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
