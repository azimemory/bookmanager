package com.kh.bookmanager.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kh.bookmanager.member.model.service.MemberService;

//이 클래스를 Configuration으로 등록
@Configuration
//Spring Security를 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	MemberService memberService;
	@Autowired
	LoginSuccessHandler loginSuccessHandler;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    //WebSecurity는 FilterChainProxy를 생성하는 필터입니다.
    public void configure(WebSecurity web) throws Exception {
    	 //정적 자원에 대해서는 security 설정을 적용하지 않음
    	 web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    //HttpSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성할 수 있습니다.
    protected void configure(HttpSecurity http) throws Exception {
    	//HttpServletRequest 요청 URL에 따라 접근 권한을 설정
        http
        	// 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
        	.authorizeRequests()
    		//antMatchers : 요청 URL의 패턴을 지정
    		//authenticated : 인증된 유저만 접근허용
    		//permitAll : 모든 유저 접근 허용
            .antMatchers("/board/**").hasRole("MEMBER")
            .antMatchers("/rent/**").hasRole("MEMBER")
            .antMatchers("/mypage/**").hasRole("MEMBER")
            .anyRequest().permitAll();
        
        //로그인 설정
        http.formLogin()
        		//사용 할 로그인 페이지 경로
                .loginPage("/member/login.do")
                //로그인 실행할 경로
                .loginProcessingUrl("/member/loginimpl.do")
                //성공시 이동할 경로
                .defaultSuccessUrl("/cart/cart.do")
                //성공 시 후속처리
                .successHandler(loginSuccessHandler)
                //설정하지 않으면 아이디값을 name 속성이 username 인 input으로 인식한다.
                //usernameParameter를 통해 변경할 수 있다.
                .usernameParameter("userId");
        
        //로그아웃 설정
        http.logout()
        		//logoutRequestMatcher : 로그아웃 경로 지정
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout.do"))
                //로그아웃 성공 시 이동할 경로 지정
                .logoutSuccessUrl("/index/index.do")
                //로그아웃 성공시 세션초기화 여부
                .invalidateHttpSession(true);
        
        //권한이 없는 사용자가 접근했을 경우 이동할 경로 지정
        http.exceptionHandling()
                .accessDeniedPage("/member/login.do");
        
        //CSRF(Cross site request forgery, 사이트간 요청 위조)
        //	-웹 어플리케이션 유저인 자신이 의도하지 않은 처리가 실행되는 취약성 또는 공격수법
        //  header에 있는 csrf값으로 권한을 열어주는데, ajax같은 비동기 통신의 경우 header에 csrf값이 없음으로
        //  아래 기능이 able 상태이면 ajax를 사용할 수 없게 된다.
        http.csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//UserDetailsService를 구현한 Service를 넘겨준다.
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
