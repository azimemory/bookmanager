package com.kh.bookmanager.book.model.vo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.bookmanager.rent.model.vo.RentBook;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity(name="tb_book")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Book{
	
	@Id
	//sequence제너레이터를 생성한다. sequenceName에 DB에 있는 sequence를 등록
	@SequenceGenerator(name = "SC_B_IDX_GEN",sequenceName = "SC_B_IDX",initialValue = 100000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE
					,generator = "SC_B_IDX_GEN")
	private int bIdx;
	private String isbn;
	private String category;
	private String title;
	private String author;
	private String info;
	private int bookAmt;
	private Date regDate;
	private int rentCnt;
	@OneToMany
	@JoinColumn(name="bIdx")
	private List<RentBook> rentBooks;
	
	public void setbIdx(String bIdx) {
		this.bIdx = Integer.parseInt(bIdx);
	}

	public int getbIdx() {
		return bIdx;
	}

	public void setbIdx(int bIdx) {
		this.bIdx = bIdx;
	}
}

