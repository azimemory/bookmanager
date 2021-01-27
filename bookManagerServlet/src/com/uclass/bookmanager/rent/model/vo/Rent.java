package com.uclass.bookmanager.rent.model.vo;

import java.sql.Date;
import java.util.List;

import com.uclass.bookmanager.book.model.vo.Book;

public class Rent {
	
	private int rmIdx;
	private int rbIdx;
	private String userId; 
	private Date regDate;
	private String isReturn;
	private String title;  
	private int rentBookCnt; 
	private List<Book> rentBookList;
	
	public Rent() {
		
	}

	/**
	 * @return the rmIdx
	 */
	public int getRmIdx() {
		return rmIdx;
	}

	/**
	 * @param rmIdx the rmIdx to set
	 */
	public void setRmIdx(int rmIdx) {
		this.rmIdx = rmIdx;
	}
	
	public void setRmIdx(String rmIdx) {
		this.rbIdx = Integer.parseInt(rmIdx);
	}
	
	public int getRbIdx() {
		return rbIdx;
	}

	public void setRbIdx(int rbIdx) {
		this.rbIdx = rbIdx;
	}
	
	public void setRbIdx(String rbIdx) {
		this.rbIdx = Integer.parseInt(rbIdx);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the isReturn
	 */
	public String getIsReturn() {
		return isReturn;
	}

	/**
	 * @param isReturn the isReturn to set
	 */
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the rentBookCnt
	 */
	public int getRentBookCnt() {
		return rentBookCnt;
	}

	/**
	 * @param rentBookCnt the rentBookCnt to set
	 */
	public void setRentBookCnt(int rentBookCnt) {
		this.rentBookCnt = rentBookCnt;
	}

	public List<Book> getRentBookList() {
		return rentBookList;
	}

	public void setRentBookList(List<Book> rentBookList) {
		this.rentBookList = rentBookList;
	}

	@Override
	public String toString() {
		return "Rent [rmIdx=" + rmIdx + ", userId=" + userId + ", regDate=" + regDate + ", isReturn=" + isReturn
				+ ", title=" + title + ", rentBookCnt=" + rentBookCnt + "]";
	}

}
