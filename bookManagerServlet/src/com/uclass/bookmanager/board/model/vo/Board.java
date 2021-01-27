package com.uclass.bookmanager.board.model.vo;

import java.sql.Date;

public class Board {
	
	private int bdIdx;
	private String userId;
	private Date regDate;
	private String title;
	private String content;
	
	public int getBdIdx() {
		return bdIdx;
	}
	
	public void setBdIdx(int bdIdx) {
		this.bdIdx = bdIdx;
	}
	
	public void setBdIdx(String bdIdx) {
		this.bdIdx = Integer.parseInt(bdIdx);
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Board [bdIdx=" + bdIdx + ", userId=" + userId + ", regDate=" + regDate + ", title=" + title
				+ ", content=" + content + "]";
	}
	
	
	

}
