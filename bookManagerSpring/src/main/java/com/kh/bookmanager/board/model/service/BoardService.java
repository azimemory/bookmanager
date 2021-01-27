package com.kh.bookmanager.board.model.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import com.kh.bookmanager.board.model.vo.Board;

public interface BoardService {
	
	public void insertBoard(Board board
						,List<MultipartFile> files						
			);
	
	public Map<String, Object> selectBoardList(
					//현재 페이지
					 int currentPage
					//페이지당 노출할 게시글 수
					,int cntPerPage);
	
	//게시물 상세
	public Map<String,Object> selectBoardDetail(String bdIdx);
	
	public void deleteFileByFIdx(String fIdx);
	
	public void updateBoard(
			Board notice
			,List<MultipartFile> files);
	
	public void deleteBoard(String bdIdx);
	
	public void deleteFileWithBoard(String bdIdx);
}
