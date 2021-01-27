package com.uclass.bookmanager.common.exception;

import com.uclass.bookmanager.common.code.ErrorCode;

public class DataAccessException extends CustomException{

	private static final long serialVersionUID = 5917417005437431074L;
	
	public DataAccessException(ErrorCode errorCode, Exception e) {
		super(errorCode,e);
	}
}
