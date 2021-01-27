package com.kh.bookmanager.rent.model.vo;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.bookmanager.book.model.vo.Book;

import lombok.Data;
@Entity(name="tb_rent_book")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
@Data
public class RentBook {
	
	@Id
	@SequenceGenerator(name = "SC_RB_IDX_GEN",sequenceName = "SC_RB_IDX"
						,initialValue = 100000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE
	,generator = "SC_RB_IDX_GEN")
	int rbIdx;
	int rmIdx;
	int bIdx;
	String userId;
	Date regDate;
	String state;
	String returnDate;
	String extentionCnt;
	@ManyToOne
	@JoinColumn(name="bIdx", insertable=false, updatable=false)
	
	Book book;
	public int getbIdx() {
		return bIdx;
	}
	public void setbIdx(int bIdx) {
		this.bIdx = bIdx;
	}
	
	
}
