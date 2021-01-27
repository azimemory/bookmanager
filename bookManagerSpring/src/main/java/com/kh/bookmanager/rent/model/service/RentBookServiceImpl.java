package com.kh.bookmanager.rent.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.bookmanager.book.model.dao.BookDao;
import com.kh.bookmanager.book.model.vo.Book;
import com.kh.bookmanager.rent.model.dao.RentBookDao;
import com.kh.bookmanager.rent.model.vo.Rent;

import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.code.MemberGrade;
import com.kh.bookmanager.common.code.Code;
import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.util.Paging;

@Service
public class RentBookServiceImpl implements RentBookService{
	
	private final RentBookDao rdao;
	private final BookDao bookDao;
	
	@Autowired
	public RentBookServiceImpl(RentBookDao rdao, BookDao bookDao) {
		this.rdao = rdao;
		this.bookDao = bookDao;
	}
	
	public Map<String,Object> selectRentinfo(String userId, int currentPage, int cntPerPage){
		
		Map<String,Object> commandMap = new HashMap<String,Object>();
		Paging paging = new Paging(rdao.totalCountInRent(userId),currentPage,cntPerPage);
		
		commandMap.put("userId",userId);
		commandMap.put("paging",paging);
		commandMap.put("data",rdao.selectRentinfo(commandMap));
		return commandMap;
	}
	
	public List<Map<String,Object>> selectRentinfo(Map<String,Object> commandMap) {
		
		System.out.println(commandMap);
		
		List<Map<String,Object>> rentInfo = rdao.selectRentbookinfo(commandMap);
		if(rentInfo == null) {
			throw new CustomException(ErrorCode.SR01);
		}
		return rentInfo;
	}
	
	public void insertRentBookInfo(Rent rent) {
		
		//연체도서가 있으면 대출 불가능
		if(rdao.selectLateCnt(rent.getUserId()) > 0 ){
			throw new CustomException(ErrorCode.IR04);
		}
		
		rdao.insertRentInfo(rent);
		
		//도서재고가 없어서 대출 불가능
		if(bookDao.selectBookAmt(rent.getRentBook().getbIdx()) < 1 ){
			throw new CustomException(ErrorCode.IR02);
		}
		
		rdao.insertRentBookInfo(rent);
	}
	
	public void insertRentBooksInfo(Rent rent) {
		
		//연체도서가 있으면 대출 불가능
		if(rdao.selectLateCnt(rent.getUserId()) > 0 ){
			throw new CustomException(ErrorCode.IR04);
		}
		
		rdao.insertRentInfo(rent);
		
		for(Book book : rent.getRentBookList()) {
			//도서재고가 없어서 대출 불가능
			if(bookDao.selectBookAmt(book.getbIdx()) < 1 ){
				throw new CustomException(ErrorCode.IR02);
			}
			
			rent.setRentBook(book);
			rdao.insertRentBookInfo(rent);
		}
	}
	
	public void updateReturnRentBook(Rent rent){
		rdao.updateReturnRentBook(rent);
	}
	
	public void updateExtendRentBook(Rent rent, String memberGrade)  {
		
		Map<String,Object> commandMap = new HashMap<String,Object>();
		commandMap.put("searchType","rbIdx");
		commandMap.put("rbIdx",rent.getRbIdx());
		
		//연장횟수 확인
		List<Map<String,Object>> curRentInfo = rdao.selectRentbookinfo(commandMap);
		
		for(Map<String,Object> rentData : curRentInfo) {
			
			int extendCnt = (int)rentData.get("extentionCnt");
			if(extendCnt <= MemberGrade.valueOf(memberGrade).EXTENDABLECNT) {
				//프로시저의 결과값은 매개변수로 넘긴 rent 객체이 들어있다.
				//vo를 사용할 경우 프로시저 결과타입이 vo의 속성 타입으로 맞춰진다.
				rdao.updateExtendRentState(rent);
			}else {
				throw new CustomException(ErrorCode.UR02);
			}
		}
	}
}
