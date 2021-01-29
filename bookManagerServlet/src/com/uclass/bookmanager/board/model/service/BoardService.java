package com.uclass.bookmanager.board.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.uclass.bookmanager.board.model.dao.BoardDao;
import com.uclass.bookmanager.board.model.vo.Board;
import com.uclass.bookmanager.common.code.ErrorCode;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.exception.CustomException;
import com.uclass.bookmanager.common.util.file.FileUtil;
import com.uclass.bookmanager.common.util.file.FileVo;
import com.uclass.bookmanager.common.util.page.Paging;

public class BoardService {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	public void insertBoard(String userId, HttpServletRequest request) {
		
		Connection conn = jdt.getConnection();
		BoardDao boardDao = new BoardDao();
		
		//게시글 저장을 위한 vo
		Board board = new Board();
		Map<String, List> boardData = null;
		
		try {
			
			//파일 저장 및 DB에 저장할 데이터 반환
			//파일 저장경로, 원본파일명, 변경파일명, Parameter를 넘겨줌
			boardData = new FileUtil().fileUpload(request);
			if(boardData == null) {
				throw new CustomException(ErrorCode.IF01);
			}
			
			board.setUserId(userId);
			board.setTitle(boardData.get("title").get(0).toString());
			board.setContent(boardData.get("content").get(0).toString());
			
			//board 테이블에 게시글 정보를 저장
			if(boardDao.insertBoard(conn,board) == 0) {
				throw new CustomException(ErrorCode.IB11);
			}
			
			//파일정보리스트를 반환 받음
			List<FileVo> fileInfoList = (List<FileVo>)boardData.get("fileInfoList");
			//파일 갯수만큼 돌면서 파일정보를 저장
			for(FileVo fileInfo : fileInfoList) {
				if(boardDao.insertFile(conn, fileInfo) == 0) {
					throw new CustomException(ErrorCode.IF01);
				}
			}
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.IB11,e);
		}finally {
			jdt.close(conn);
		}
	}

	public Map<String, Object> selectBoardList (
			//현재 페이지
			 int currentPage
			//페이지당 노출할 게시글 수
			,int cntPerPage) {
			
		BoardDao boardDao = new BoardDao();
		Connection conn = jdt.getConnection();
		Map<String, Object> commandMap = new HashMap<String, Object>();
		
		//페이징 처리를 위한 객체 생성
		Paging p = null;
		
		try {
			p = new Paging(boardDao.selectContentCnt(conn),currentPage,cntPerPage);
			//현재 페이지에 필요한 게시물 목록
			List<Board> blist = boardDao.selectBoardList(conn,p);
			commandMap.put("blist", blist);
			commandMap.put("paging", p);
			jdt.commit(conn);
		} catch (SQLException e) {
			throw new CustomException(ErrorCode.SB11,e);
		}finally {
			jdt.close(conn);
		}
		
		return commandMap;
	}

	//게시물 상세
	public Map<String,Object> selectBoardDetail(String bdIdx) {
		Map<String,Object> commandMap = new HashMap<String, Object>();
		
		BoardDao boardDao = new BoardDao();
		Connection conn = jdt.getConnection();
		List<FileVo> flist = null; 
		
		try {
			Board board = boardDao.selectBoardDetail(conn,bdIdx);
			if(board == null) {
				throw new CustomException(ErrorCode.SB12);
			}
			
			flist = boardDao.selectFileWithBoard(conn,bdIdx);
			commandMap.put("board",board);
			commandMap.put("flist",flist);
			jdt.commit(conn);
		} catch (SQLException e) {
			throw new CustomException(ErrorCode.SB11,e);
		}finally {
			jdt.close(conn);
		}
		
		return commandMap;
	}

	public void deleteFileWithFIdx(String fIdx, String root) {
		
		BoardDao boardDao = new BoardDao();
		Connection conn = jdt.getConnection();
		
		//삭제할 파일의 저장경로를 받아온다.
		FileVo fileData = null;
		try {
			
			fileData = boardDao.selectFileWithFidx(conn,fIdx);
			if(fileData == null) {
				throw new CustomException(ErrorCode.SF01);
			}
			//저장경로를 fileUtil.deletFile 메서드의 매개변수로 넘겨서
			//해당 파일을 삭제한다.
			FileUtil fileUtil = new FileUtil();
			String savePath = root + fileData.getSavePath();
			fileUtil.deleteFile(savePath);
			
			//파일테이블에서 파일정보를 삭제한다.
			if(boardDao.deleteFileWithFIdx(conn,fIdx) == 0) {
				throw new CustomException(ErrorCode.DF01);
			};
			
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.DF01,e);
		}finally {
			jdt.close(conn);
		}
	}

	public void deleteFileWithBoard(String bdIdx) {
		BoardDao boardDao = new BoardDao();
		Connection conn = jdt.getConnection();
		
		List<FileVo> files = null;
		try {
			files = boardDao.selectFileWithBoard(conn,bdIdx);
			if(files.size() > 0) {
				for(FileVo fileData : files) {
					FileUtil fileUtil = new FileUtil();
					fileUtil.deleteFile(fileData.getSavePath());
				}
				
				if(boardDao.deleteFileWithBoard(conn,bdIdx) == 0) {
					throw new CustomException(ErrorCode.DF01);
				};
			}
			
			jdt.commit(conn);
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.DF01,e);
		}finally {
			jdt.close(conn);
		}
	}

	public void updateBoard(HttpServletRequest request) {
		
		BoardDao boardDao = new BoardDao();
		Connection conn = jdt.getConnection();
		Map<String, List> boardData = null;
		
		//게시글 저장을 위한 vo
		Board board = new Board();
		
		try {
			//파일 저장 경로
			String root = request.getServletContext().getRealPath("/resources/upload/");
			boardData = new FileUtil().fileUpload(request);
			board.setBdIdx(boardData.get("bdIdx").get(0).toString());
			board.setTitle(boardData.get("title").get(0).toString());
			board.setContent(boardData.get("content").get(0).toString());
			
			List<String> rmFiles = null;
			rmFiles = (List)boardData.get("rmFiles");
			
			if(rmFiles != null) {
				for(String fIdx : rmFiles) {
					deleteFileWithFIdx(fIdx, root);
				}
			}
			
			//board 테이블에 게시글 정보를 저장
			boardDao.updateBoard(conn,board);
			
			//파일정보리스트를 반환 받음
			List<FileVo> fileInfoList = (List<FileVo>)boardData.get("fileInfoList");
			//파일 갯수만큼 돌면서 파일정보를 저장
			if(fileInfoList != null) {
				for(FileVo fileInfo : fileInfoList) {
					fileInfo.setTypeIdx(board.getBdIdx());
					if(boardDao.insertFile(conn, fileInfo) == 0) {
						throw new CustomException(ErrorCode.IF01);
					}
				}
			}
			jdt.commit(conn);
			
		} catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.UB11,e);
		} finally {
			jdt.close(conn);
		}
	}

	public void deleteBoard(String bdIdx) {
		
		BoardDao boardDao = new BoardDao();
		Connection conn = jdt.getConnection();
		deleteFileWithBoard(bdIdx);
		
		try {
			if(boardDao.deleteBoard(conn,bdIdx) == 0) {
				throw new CustomException(ErrorCode.DB11);
			};
			jdt.commit(conn);
		}catch (SQLException e) {
			jdt.rollback(conn);
			throw new CustomException(ErrorCode.DB11,e);
		}finally {
			jdt.close(conn);
		}
	}
}
