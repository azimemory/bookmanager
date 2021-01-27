package com.kh.bookmanager.cart.model.vo;

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

import com.kh.bookmanager.board.model.vo.Board;
import com.kh.bookmanager.book.model.vo.Book;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name="tb_book_cart")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Cart {
	
	@SequenceGenerator(name = "SC_BC_IDX_GEN",sequenceName = "SC_BC_IDX", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SC_BC_IDX_GEN")
	private int bcIdx;
	private String userId;
	private int bIdx;
	private Date regDate;
	private String sessionId;
	
	@ManyToOne
	@JoinColumn(name="bIdx", insertable = false, updatable = false)
	private Book book;

	public int getbIdx() {
		return bIdx;
	}

	public void setbIdx(int bIdx) {
		this.bIdx = bIdx;
	}
}
