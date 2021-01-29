package com.kh.bookmanager.cart.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.bookmanager.cart.model.dao.CartDao;
import com.kh.common.code.ErrorCode;
import com.kh.common.exception.CustomException;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartDao cartDao;
	
	@Autowired
	public CartServiceImpl(CartDao cartDao) {
		this.cartDao = cartDao;
	}
	
	public List<Map<String, Object>> selectCart(Map<String, Object> commandMap)  {
		
		List<Map<String,Object>> cartList = cartDao.selectCart(commandMap);
		
		if(cartList != null) {
			return cartList;
		}else {
			throw new CustomException(ErrorCode.SC01);
		}
	}
	
	public void insertCart(Map<String, Object> commandMap)   {
		
		List<Map<String,Object>> curCart = selectCart(commandMap);
		
		for(Map<String,Object> cart: curCart ) {
			//number 타입의 컬럼은 java.math.BigDecimal로 매핑된다.
			//따라서 String으로 파싱한 다음 비교해준다.
			if(cart.get("bIdx").equals(commandMap.get("bIdx"))) {
				throw new CustomException(ErrorCode.SC02);
			}
		}
		
		if(cartDao.insertCart(commandMap) == 0) {
			throw new CustomException(ErrorCode.IC01);
		}
	}
	
	public void deleteCart(String bcIdx)  {
		if(cartDao.deleteCart(bcIdx) == 0) {
			throw new CustomException(ErrorCode.DC01);
		};
	}
}
