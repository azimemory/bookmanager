package com.kh.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kh.common.code.ErrorCode;

public class CustomException extends RuntimeException{
	
	Logger logger = LoggerFactory.getLogger(CustomException.class);
	
	public final ErrorCode ERRORCODE;
	
	//message : Exception을 생성할 때 생성자의 매개변수로
	//문자열을 넘기면, getMessage 메서드를 통해 다시 사용할 수 있다.
	public CustomException(ErrorCode errorCode) {
		ERRORCODE = errorCode;
	}
	
	public CustomException(ErrorCode errorCode, Exception e) {
		e.printStackTrace();
		ERRORCODE = errorCode;
	}
}
