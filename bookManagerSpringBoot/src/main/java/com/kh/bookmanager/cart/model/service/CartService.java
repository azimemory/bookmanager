package com.kh.bookmanager.cart.model.service;

import java.util.List;
import java.util.Map;

import com.kh.bookmanager.cart.model.vo.Cart;

import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.exception.CustomException;

public interface CartService {
	
	public List<Cart> selectCart(Cart cart) ;
	
	public void insertCart(Cart cart) ;
	
	public void deleteCart(int bcIdx) ;

}
