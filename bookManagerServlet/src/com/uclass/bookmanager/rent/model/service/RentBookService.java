package com.uclass.bookmanager.rent.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uclass.bookmanager.book.model.dao.BookDao;
import com.uclass.bookmanager.book.model.service.BookService;
import com.uclass.bookmanager.book.model.vo.Book;
import com.uclass.bookmanager.rent.model.dao.RentBookDao;
import com.uclass.bookmanager.rent.model.vo.Rent;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.code.MemberGrade;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.exception.CustomException;
import com.uclass.bookmanager.common.util.Paging;

public class RentBookService {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public Map<String,Object> selectRentinfo(String id, int currentPage, int cntPerPage){
		RentBookDao rdao = new RentBookDao();
		Map<String,Object> commandMap = null;
		List<Rent> rentList = null;
		
		Connection conn = jdt.getConnection();
		
		try {
			int total = rdao.totalCountInRent(conn, id);
			Paging paging = new Paging(total,currentPage,cntPerPage);
			rentList = rdao.selectRentinfo(conn, id, paging);
			commandMap = new HashMap<String, Object>();
			commandMap.put("paging",paging);
			commandMap.put("data",rentList);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.SR01,e);
		}finally {
			jdt.close(conn);
		}
		
		return commandMap;
	}
	
	public List<Map<String,Object>> selectRentinfo(String searchType, String rmIdx){
		RentBookDao rdao = new RentBookDao();
		Connection conn = jdt.getConnection();
		List<Map<String,Object>> result = null;
		
		try {
			result = rdao.selectRenbooktinfo(conn, searchType, rmIdx);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.SR01,e);
		}finally {
			jdt.close(conn);
		}
		
		return result;
	}
	
	public void insertRentBookInfo(Rent rent){
		
		RentBookDao rdao = new RentBookDao();
		Connection conn = jdt.getConnection();
		BookDao bookDao = new BookDao();
				
		try {
			
			//연체도서가 있으면 대출 불가능
			if(rdao.selectLateCnt(conn, rent.getUserId()) > 0 ){
				throw new CustomException(ErrorCode.IR04);
			}
			
			rdao.insertRentInfo(conn,rent);
			for(Book book : rent.getRentBookList()) {
				//도서재고가 없어서 대출 불가능
				if(bookDao.selectBookAmt(conn, String.valueOf(book.getbIdx())) < 1 ){
					throw new CustomException(ErrorCode.IR04);
				}
				rdao.insertRentBookInfo(conn, book.getbIdx());
			}
			
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.IR01,e);
		}finally {
			jdt.close(conn);
		}
		
	}
	
	public void updateReturnRentBook(Rent rent){
		RentBookDao rdao = new RentBookDao();
		Connection conn = jdt.getConnection();
		
		try {
			rdao.updateReturnRentBook(conn, rent);
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.UR01,e);
		}finally {
			jdt.close(conn);
		}
	}
	
	public void updateExtendRentBook(String rmIdx, String rbIdx, String memberGrade){
		RentBookDao rdao = new RentBookDao();
		Connection conn = jdt.getConnection();
		
		try {
			//쿼리로 넘기기
			//rdao.updateExtendRentBook(conn, rb_idx);
			//rdao.insertUpdateHistory(conn, rb_idx);
			//프로시저 활용해서 update + insert 한번에 해결하기
			
			//연장횟수 확인
			List<Map<String,Object>> curRentInfo = rdao.selectRenbooktinfo(conn,"rbIdx",rbIdx);
			int extendCnt = (int)curRentInfo.get(0).get("extentionCnt");
			
			if(extendCnt <= MemberGrade.valueOf(memberGrade).extendableCnt()) {
				rdao.updateExtendRentState(conn, rmIdx ,rbIdx);	
			}else {
				throw new CustomException(ErrorCode.UR02);
			}
			
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.UR01,e);
		}finally {
			jdt.close(conn);
		}
	}
}
