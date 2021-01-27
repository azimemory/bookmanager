package com.kh.bookmanager.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.kh.bookmanager.member.model.vo.Member;

//로그인 성공, 실패 로직을 작성하기 위한 CustomAuthenticationProvider
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UserDetailsService userDeSer;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		//form에서 전달된 userId
		String userId = (String)authentication.getPrincipal();
		//form에서 전달된 password
        String password = (String)authentication.getCredentials(); 
        
        Member member = (Member)userDeSer.loadUserByUsername(userId);
        if(!password.equals(member.getPassword())) {
        	  throw new BadCredentialsException(userId);
        }
        
        if(member.getIsLeave() == 1) {
        	throw new BadCredentialsException(userId);
        }
        
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userId, password, roles);
        result.setDetails(member);
		return result;
	}

	@Override
	//앞에서 필터에서 보내준 Authentication 객체를 이 AuthenticationProvider가
	//인증 가능한 클래스인지 확인하는 메서드다.
	public boolean supports(Class<?> authentication) {
		//id, password 형식이면  true 아니면 false
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
