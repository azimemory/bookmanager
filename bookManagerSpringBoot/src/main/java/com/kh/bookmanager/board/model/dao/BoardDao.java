package com.kh.bookmanager.board.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.bookmanager.board.model.vo.Board;
import com.kh.bookmanager.board.model.vo.QBoard;
import com.kh.bookmanager.common.code.Code;
import com.kh.bookmanager.common.util.FileVo;
import com.kh.bookmanager.common.util.Paging;
import com.kh.bookmanager.common.util.QFileVo;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BoardDao {
	
	@Autowired
	JPAQueryFactory jpaQuery;
	@Autowired
	EntityManager em;
	QFileVo f = QFileVo.fileVo;
	QBoard b = QBoard.board;
	
	//게시물 테이블에 게시물 추가
	public void insertBoard(Board board) {
		em.persist(board);
	}
	
	//파일 테이블에 파일정보 추가
	public void insertFile(FileVo fileVo) {
		em.persist(fileVo);
	}
	
	//게시글 목록
	public List<Board> selectBoardList(Paging page){
		return jpaQuery.select(b)
				.from(b)
				.orderBy(b.bdIdx.desc())
				.offset(page.getStart()-1)
				.limit(page.getCntPerPage())
				.fetch();
	}
	
	//전체 게시글 숫자 반환
	public int selectContentCnt() {
		return (int)jpaQuery.select(b).from(b).fetchCount();
	}
	
	//게시글 상세
	public Board selectBoardDetail(int bdIdx) {
		return jpaQuery.select(b).from(b).where(b.bdIdx.eq(bdIdx)).fetchOne();
	}
	
	//게시글 파일 정보
	public List<FileVo> selectFileWithBoard(int bdIdx){
		return jpaQuery.select(f).from(f).where(f.typeIdx.eq(bdIdx)).fetch();
	}
	
	//파일 인덱스로 파일정보 반환하기
	public FileVo selectFileWithFidx(int fIdx){
		return jpaQuery.select(f).from(f).where(f.fIdx.eq(fIdx)).fetchOne();
	}
	
	//파일 인덱스로 파일정보 삭제하기
	public int deleteFileByFIdx(int fIdx) {
		return (int)jpaQuery.delete(f).where(f.fIdx.eq(fIdx)).execute(); 
	}
	
	//파일테이블에서 해당 게시글번호의 파일 삭제
	public int deleteFileWithBoard(int bdIdx){
		return (int)jpaQuery.delete(f).
				where(f.typeIdx.eq(bdIdx)
						.and(f.type.eq(Code.UPLOAD_TYPE_BOARD.VALUE)))
				.execute();
	}
	
	//게시글 수정
	public int updateBoard(Board board) {
		return (int)jpaQuery.update(b)
				.set(b.title, board.getTitle())
				.set(b.content, board.getContent())
				.where(b.bdIdx.eq(board.getBdIdx()))
				.execute();
	}
	
	//게시글 삭제
	public int deleteBoard(int bdIdx){
		return (int)jpaQuery.delete(b).where(b.bdIdx.eq(bdIdx)).execute();
	}
	
}
