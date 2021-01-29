package com.kh.bookmanager.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kh.common.exception.CustomException;

//ControllerAdvice : 지정한 패키지 내의 모든 클래스가
//@ExceptionHandler 어노테이션이 지정된 
//메소드를 공유하도록 해준다.
@ControllerAdvice(basePackages = {"com.kh.bookmanager"})
@Controller
public class ExceptionController {
	
	@ExceptionHandler(CustomException.class)
	public void serviceException(CustomException e, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String prefix = "/WEB-INF/views/";
		String contentType = request.getContentType();
		System.out.println("contentType >>>>>>>>> " + contentType);
		//비동기통신 처리
		if(contentType != null && contentType.equals("application/json")) {
			//exception이 발생하면 500상태코드가 전송된다. 200 또는 201 외의 코드로 날아가면
			//error처리가 되기 때문에 필요하다면 200 상태코드로 응답을 전송
			//response.setStatus(200);
			response.setStatus(418);
			response.setContentType("text/html");
		    response.setCharacterEncoding("utf8");
		    response.getWriter().print(e.ERRORCODE.MESSAGE);
		}else {
			request.setAttribute("alertMsg", e.ERRORCODE.MESSAGE);
			request.setAttribute("url", e.ERRORCODE.URL);
			RequestDispatcher rd = request.getRequestDispatcher(prefix + "common/result.jsp");
			rd.forward(request, response);
		}
	}
	
	/*
	 * @ExceptionHandler(CustomException.class) public void
	 * asyncException(CustomException e, HttpServletResponse response) throws
	 * IOException{ PrintWriter pw = response.getWriter();
	 * pw.print(e.ERRORCODE.MESSAGE); }
	 */
	
	@ExceptionHandler(Exception.class)
	//예외처리를 일괄적으로 할 수 있게 도와주는 annotation
	//Controller에서만 사용이 가능하다.
	@ResponseStatus(value=HttpStatus.I_AM_A_TEAPOT)
	public String exception(Exception e, Model model
						, HttpServletRequest request, HttpServletResponse response) {
		
		e.printStackTrace();
		model.addAttribute("alertMsg", "에러가 발생했습니다.");
		model.addAttribute("url", "/index/index.do");
		return "common/result";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
