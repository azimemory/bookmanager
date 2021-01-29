package com.uclass.bookmanager.common.exception;

import com.uclass.bookmanager.common.code.ErrorCode;

public class ToAlertException extends CustomException{
	
	private static final long serialVersionUID = -9130205160292089243L;

	public ToAlertException(ErrorCode error) {
		super(error);
	}
	
	public ToAlertException(ErrorCode error, Exception e) {
		super(error,e);
	}
}
