package com.kh.bookmanager.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.kh.bookmanager.board.model.dao.BoardDao;
import com.kh.bookmanager.board.model.vo.Board;
import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.util.FileUtil;
import com.kh.bookmanager.common.util.FileVo;
import com.kh.bookmanager.common.util.Paging;

public interface BoardService {
	
	/* @Transactional */
	//스프링에게 트랜잭션 관리를 위임하는 어노테이션
	//METHOD 또는 CLASS 작성할 수 있다.
	//CLASS에 지정할 경우, 해당 클래스의 모든 메서드가
	//스프링에게 트랜잭션 관리를 위임하게 된다.
	public void insertBoard(Board board
						,List<MultipartFile> files
			);
	
	public Map<String, Object> selectBoardList(
					//현재 페이지
					 int currentPage
					//페이지당 노출할 게시글 수
					,int cntPerPage);
	
	//게시물 상세
	public Map<String,Object> selectBoardDetail(int bdIdx);
	
	public void deleteFileByFIdx(int fIdx);
	
	public void updateBoard(
			Board board
			,List<MultipartFile> files);
	
	public void deleteBoard(int bdIdx);
	
	public void deleteFileWithBoard(int bdIdx);
	

}
