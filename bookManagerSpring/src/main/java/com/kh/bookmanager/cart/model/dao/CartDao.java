package com.kh.bookmanager.cart.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<Map<String, Object>> selectCart(Map<String, Object> commandMap){
		return sqlSession.selectList("CART.selectCart",commandMap);
	}
	
	public int insertCart(Map<String, Object> commandMap){
		return sqlSession.insert("CART.insertCart",commandMap);
	}
	
	public int deleteCart(String bcIdx){
		return sqlSession.delete("CART.deleteCart",bcIdx);
	}

}
