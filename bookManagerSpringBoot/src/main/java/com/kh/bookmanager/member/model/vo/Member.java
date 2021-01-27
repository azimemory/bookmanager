package com.kh.bookmanager.member.model.vo;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kh.bookmanager.common.code.MemberGrade;

import lombok.Getter;
import lombok.Setter;

@Entity(name="tb_member")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Member implements UserDetails{

	@Id
	private String userId;
	private String password;
	private String grade;
	private String tell;
	private String email;
	private Date regDate;
	private Date rentableDate;
	private int isLeave;
	
	@Transient
	private int rentCnt;
	
	//Spring Security 설정을 위해 UserDetails 인테페이스의 메서드를 구현한 것들
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		//원하는 권한 (ROLE_MEMBER)를 등록한다. 반드시 앞에 ROLE_가 있어야 한다.
		grantedAuthorities.add(new SimpleGrantedAuthority(MemberGrade.valueOf(grade).ROLE));
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String toString() {
		return "Member [userId=" + userId + ", password=" + password + ", grade=" + grade + ", tell=" + tell
				+ ", email=" + email + ", regDate=" + regDate + ", rentableDate=" + rentableDate + "]";
	}

}
