package com.uclass.bookmanager.common.exception;

import com.uclass.bookmanager.common.code.ErrorCode;

public class CustomException extends RuntimeException{
	private static final long serialVersionUID = 5917417005437431074L;
	public final ErrorCode ERRORCODE;
	
	//message : Exception을 생성할 때 생성자의 매개변수로
	//문자열을 넘기면, getMessage 메서드를 통해 다시 사용할 수 있다.
	public CustomException(ErrorCode errorCode) {
		ERRORCODE = errorCode;
		//ErrorCode만 넘어오는 경우에는 에러가 발생한 것이 아니라 우리가 지정한 예외상황이기 때문에
		//stackTrace를 찍지 않는다.
		this.setStackTrace(new StackTraceElement[0]);
	}
	
	public CustomException(ErrorCode errorCode, Exception e) {
		e.printStackTrace();
		ERRORCODE = errorCode;
	}
}
