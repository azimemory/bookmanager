package com.kh.bookmanager.rent.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.bookmanager.rent.model.vo.Rent;

@Repository
public class RentBookDao {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public int totalCountInRent(String userId){
		return sqlSession.selectOne("RENT.totalCountInRent",userId);
	}
	
	public List<Rent> selectRentinfo(Map<String,Object> commandMap){
		return sqlSession.selectList("RENT.selectRentinfo",commandMap);
	}
	
	public List<Map<String,Object>> selectRentbookinfo(Map<String,Object> commandMap){
		return sqlSession.selectList("RENT.selectRentbookinfo",commandMap);
	}

	public int insertRentInfo(Rent rent){
		return sqlSession.insert("RENT.insertRentInfo",rent);
	}

	public void insertRentBookInfo(Rent rent){
		sqlSession.insert("RENT.insertRentBookInfo",rent);
	}

	public void updateReturnRentBook(Rent rent){
		sqlSession.update("RENT.updateReturnRentBook",rent);
	}
	
	public void updateExtendRentState(Rent rent){
		sqlSession.update("RENT.updateExtendRentState",rent);
	}
	
	//연체도서 검사
	public int selectLateCnt(String userId){
		return sqlSession.selectOne("RENT.selectLateCnt",userId);
	}
}
