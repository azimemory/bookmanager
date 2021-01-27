package com.kh.bookmanager.common.util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.bookmanager.board.model.vo.Board;

@Entity(name="tb_file")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
public class FileVo {
	
	@SequenceGenerator(name = "SC_FILE_IDX_GEN",sequenceName = "SC_FILE_IDX", allocationSize = 1)
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SC_FILE_IDX_GEN")
	int fIdx;
	int typeIdx;
	String type;
	String originFileName;
	String renameFileName;
	String savePath;
	String isDel;
	@ManyToOne
	@JoinColumn(name = "typeIdx", insertable = false, updatable = false)
	Board notice;
	public int getfIdx() {
		return fIdx;
	}
	public void setfIdx(int fIdx) {
		this.fIdx = fIdx;
	}
	public int getTypeIdx() {
		return typeIdx;
	}
	public void setTypeIdx(int typeIdx) {
		this.typeIdx = typeIdx;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	public String getRenameFileName() {
		return renameFileName;
	}
	public void setRenameFileName(String renameFileName) {
		this.renameFileName = renameFileName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public Board getNotice() {
		return notice;
	}
	public void setNotice(Board notice) {
		this.notice = notice;
	}
	@Override
	public String toString() {
		return "FileVo [fIdx=" + fIdx + ", typeIdx=" + typeIdx + ", type=" + type + ", originFileName=" + originFileName
				+ ", renameFileName=" + renameFileName + ", savePath=" + savePath + ", isDel=" + isDel + ", notice="
				+ notice + "]";
	}
	
	
}
