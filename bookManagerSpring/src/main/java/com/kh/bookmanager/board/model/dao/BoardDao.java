package com.kh.bookmanager.board.model.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.bookmanager.board.model.vo.Board;
import com.kh.bookmanager.common.util.FileVo;
import com.kh.bookmanager.common.util.Paging;


@Repository
public class BoardDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//게시물 테이블에 게시물 추가
	public int insertBoard(Board board) {
		return sqlSession.insert("Board.insertBoard", board);
	}
	
	//파일 테이블에 파일정보 추가
	public int insertFile(FileVo fileInfo) {
		return sqlSession.insert("Board.insertFile",fileInfo);
	}
	
	//게시글 목록
	public List<Board> selectBoardList(Paging page){
		return sqlSession.selectList("Board.selectBoardList",page);
	}
	
	//전체 게시글 숫자 반환
	public int selectContentCnt() {
		return sqlSession.selectOne("Board.selectContentCnt");
	}
	
	//게시글 상세
	public Board selectBoardDetail(String bdIdx) {
		return sqlSession.selectOne("Board.selectBoardDetail",bdIdx);
	}
	
	//게시글 파일 정보
	public List<FileVo> selectFileWithBoard(String bdIdx){
		return sqlSession.selectList("Board.selectFileWithBoard",bdIdx);
	}
	
	//파일 인덱스로 파일정보 반환하기
	public FileVo selectFileWithFidx(String fIdx){
		return sqlSession.selectOne("Board.selectFileWithFIdx",fIdx);
	}
	
	//파일 인덱스로 파일정보 삭제하기
	public int deleteFileByFIdx(String fIdx) {
		return sqlSession.delete("Board.deleteFileByFIdx",fIdx);
	}
	
	//파일테이블에서 해당 게시글번호의 파일 삭제
	public int deleteFileWithBoard(String fmIdx){
		return sqlSession.delete("Board.deleteFileWithBoard", fmIdx);
	}
	
	//게시글 수정
	public int updateBoard(Board board) {
		return sqlSession.update("Board.updateBoard", board);
	}
	
	//게시글 삭제
	public int deleteBoard(String bdIdx){
		return sqlSession.delete("Board.deleteBoard", bdIdx);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
