package com.kh.bookmanager.cart.model.service;

import java.util.List;
import java.util.Map;

import com.kh.common.exception.CustomException;

public interface CartService {
	
	public List<Map<String,Object>> selectCart(Map<String, Object> commandMap) ;
	
	public void insertCart(Map<String, Object> commandMap) ;
	
	public void deleteCart(String bcIdx) ;

}
