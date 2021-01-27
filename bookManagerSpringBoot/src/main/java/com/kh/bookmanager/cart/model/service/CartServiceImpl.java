package com.kh.bookmanager.cart.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.bookmanager.cart.model.dao.CartDao;
import com.kh.bookmanager.cart.model.vo.Cart;

import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.exception.CustomException;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartDao cartDao;
	
	@Autowired
	public CartServiceImpl(CartDao cartDao) {
		this.cartDao = cartDao;
	}
	
	public List<Cart> selectCart(Cart cart)  {
		
		List<Cart> cartList = cartDao.selectCart(cart);
		
		if(cartList != null) {
			return cartList;
		}else {
			throw new CustomException(ErrorCode.SC01);
		}
	}
	
	public void insertCart(Cart cart)   {
		
		List<Cart> curCart = selectCart(cart);
		
		for(Cart c: curCart ) {
			//number 타입의 컬럼은 java.math.BigDecimal로 매핑된다.
			//따라서 String으로 파싱한 다음 비교해준다.
			if(c.getbIdx() == (cart.getBcIdx())) {
				throw new CustomException(ErrorCode.SC02);
			}
		}
		
		cartDao.insertCart(cart);
	}
	
	public void deleteCart(int bcIdx)  {
		if(cartDao.deleteCart(bcIdx) == 0) {
			throw new CustomException(ErrorCode.DC01);
		};
	}
}
