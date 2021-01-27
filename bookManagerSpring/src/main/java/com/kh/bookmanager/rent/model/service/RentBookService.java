package com.kh.bookmanager.rent.model.service;

import java.util.List;
import java.util.Map;
import com.kh.bookmanager.rent.model.vo.Rent;

import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.exception.CustomException;

public interface RentBookService {
	
	public Map<String,Object> selectRentinfo(String id, int currentPage, int cntPerPage);
	
	public List<Map<String,Object>> selectRentinfo(Map<String,Object> commandMap) ;
	
	public void insertRentBookInfo(Rent rent) ;
	
	public void insertRentBooksInfo(Rent rent) ;
	
	public void updateReturnRentBook(Rent rent);
	
	public void updateExtendRentBook(Rent rent, String memberGrade) ;

	

}
