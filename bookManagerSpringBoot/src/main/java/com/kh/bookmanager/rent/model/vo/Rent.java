package com.kh.bookmanager.rent.model.vo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.bookmanager.book.model.vo.Book;

import lombok.Data;

@Entity(name="tb_rent_master")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
@Data
public class Rent {
	
	@Id
	@SequenceGenerator(name = "SC_RM_IDX_GEN",sequenceName = "SC_RM_IDX", allocationSize = 1 )
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SC_RM_IDX_GEN")
	private int rmIdx;
	@Transient
	private int rbIdx;
	private String userId; 
	private Date regDate;
	private String isReturn;
	private String title;
	
	@OneToMany
	@JoinColumn(name="rmIdx")
	private List<RentBook> rentBook;
	@Transient
	private Book book;
	@Transient
	private List<Book> rentBookList;
	private int rentBookCnt;
}
