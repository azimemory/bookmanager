package com.kh.bookmanager.board.model.vo;

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

import com.kh.bookmanager.common.util.FileVo;

import lombok.Getter;
import lombok.Setter;

@Entity(name="tb_board")
//insert 시 null인 필드를 빼고 테이블에 추가한다.
//해당 어노테이션이 없으면 not null이 있는 테이블에서 에러가 나거나
//컬럼에 default로 지정한 값이 적용되지 않는다.
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Board {
	//sequence제너레이터를 생성한다. sequenceName에 DB에 있는 sequence를 등록
	@SequenceGenerator(name = "SC_BOARD_IDX_GEN",sequenceName = "SC_BOARD_IDX", allocationSize = 1)
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "SC_BOARD_IDX_GEN")
	private int bdIdx;
	private String userId;
	private Date regDate;
	private String title;
	private String content;
	@OneToMany
	@JoinColumn(name = "typeIdx", insertable = false, updatable = false)
	List<FileVo> fileVos;

}
