package com.kh.bookmanager.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.bookmanager.board.model.dao.BoardDao;
import com.kh.bookmanager.board.model.vo.Board;
import com.kh.bookmanager.common.code.Code;
import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;
import com.kh.bookmanager.common.util.FileUtil;
import com.kh.bookmanager.common.util.FileVo;
import com.kh.bookmanager.common.util.Paging;

@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardDao boardDao;
	
	@Autowired
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao =boardDao;
	}
	
	/* @Transactional */
	//스프링에게 트랜잭션 관리를 위임하는 어노테이션
	//METHOD 또는 CLASS 작성할 수 있다.
	//CLASS에 지정할 경우, 해당 클래스의 모든 메서드가
	//스프링에게 트랜잭션 관리를 위임하게 된다.
	public void insertBoard(Board board
						,List<MultipartFile> files
			)  {
		
		boardDao.insertBoard(board);
		
		if(!(files.size() == 1
			&& files.get(0).getOriginalFilename().equals(""))) {
			
			//파일업로드를 위해 FileUtil.fileUpload() 호출
			List<FileVo> filedata 
					= new FileUtil().fileUpload(files);
			
			for(FileVo f : filedata) {
				f.setType(Code.UPLOAD_TYPE_BOARD.VALUE);
				f.setTypeIdx(board.getBdIdx());
				boardDao.insertFile(f);
			}
		}
	}
	
	public Map<String, Object> selectBoardList(
					//현재 페이지
					 int currentPage
					//페이지당 노출할 게시글 수
					,int cntPerPage) {
		
		 Map<String, Object> commandMap 
		 		= new HashMap<String, Object>();
		 //페이징 처리를 위한 객체 생성
		 Paging p = new Paging(boardDao.selectContentCnt()
				 ,currentPage,cntPerPage);
		 
		 //현재 페이지에 필요한 게시물 목록
		 List<Board> blist = boardDao.selectBoardList(p);
		 
		 if(blist == null) {
			 throw new CustomException(ErrorCode.SB11);
		 }
		 
		 commandMap.put("blist", blist);
		 commandMap.put("paging", p);
				 
		return commandMap;
	}
	
	//게시물 상세
	public Map<String,Object> selectBoardDetail(int bdIdx) {
		
		Map<String,Object> commandMap = new HashMap<String, Object>();
		
		Board board = boardDao.selectBoardDetail(bdIdx);
		List<FileVo> flist = boardDao.selectFileWithBoard(bdIdx);
		
		if(board == null) {
			throw new CustomException(ErrorCode.SB11);
		}else if(flist == null) {
			throw new CustomException(ErrorCode.SF01);
		}
		
		commandMap.put("board",board);
		commandMap.put("flist",flist);
		return commandMap;
	}
	
	public void deleteFileByFIdx(int fIdx)  {
		
		//삭제할 파일의 저장경로를 받아온다.
		FileVo fileData
			= boardDao.selectFileWithFidx(fIdx);
		
		if(fileData == null) {
			throw new CustomException(ErrorCode.SF01);
		}
		
		//파일테이블에서 파일정보를 삭제한다.
		if(boardDao.deleteFileByFIdx(fIdx) == 0) {
			throw new CustomException(ErrorCode.DF01);
		};
		
		//저장경로를 fileUtil.deletFile 메서드의 매개변수로 넘겨서
		//해당 파일을 삭제한다.
		FileUtil fileUtil = new FileUtil();
		fileUtil.deleteFile(fileData.getSavePath());
	}
	
	public void updateBoard(
			Board board
			,List<MultipartFile> files)  {
		
		if(boardDao.updateBoard(board) == 0) {
			throw new CustomException(ErrorCode.UB11);
		};
		
		if(!(files.size() == 1 && files.get(0).getOriginalFilename().equals(""))) {
			List<FileVo> filedata = new FileUtil().fileUpload(files);
			
			for(FileVo fileInfo : filedata) {
				fileInfo.setType(Code.UPLOAD_TYPE_BOARD.VALUE);
				fileInfo.setTypeIdx(board.getBdIdx());
				boardDao.insertFile(fileInfo);
			}
		}
	}
	
	public void deleteBoard(int bdIdx)  {
		deleteFileWithBoard(bdIdx);
		if(boardDao.deleteBoard(bdIdx) == 0) {
			throw new CustomException(ErrorCode.DB11);
		};
	}
	
	public void deleteFileWithBoard(int bdIdx) {
		
		List<FileVo> files = boardDao.selectFileWithBoard(bdIdx);
		if(files.size() > 0) {
			for(FileVo fileData : files) {
				FileUtil fileUtil = new FileUtil();
				fileUtil.deleteFile(fileData.getSavePath());
			}
			
			boardDao.deleteFileWithBoard(bdIdx);
		}
	}
}
