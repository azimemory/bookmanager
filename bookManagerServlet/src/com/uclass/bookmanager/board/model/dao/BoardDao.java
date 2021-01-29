package com.uclass.bookmanager.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uclass.bookmanager.board.model.vo.Board;
import com.uclass.bookmanager.common.code.Code;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.util.file.FileVo;
import com.uclass.bookmanager.common.util.page.Paging;

public class BoardDao {
	
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
		//게시물 테이블에 게시물 추가
		public int insertBoard(Connection conn, Board Board) throws SQLException {
			
			int res = 0;
			String sql = "insert into tb_Board" + 
					"(bd_idx, user_id, reg_date, title, content)" + 
					"values(" + 
					"SC_Board_IDX.nextval," + 
					"?, sysdate, ?, ?)"; 
			
			PreparedStatement pstm = null;
			
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, Board.getUserId());
				pstm.setString(2, Board.getTitle());
				pstm.setString(3, Board.getContent());
				res = pstm.executeUpdate();
			} finally {
				jdt.close(pstm);
			}
			
			return res;
		}
		
		//파일 테이블에 파일정보 추가
		public int insertFile(Connection conn, FileVo fileInfo) throws SQLException {
			int res = 0;
			String bdIdx = "";
			if(fileInfo.getTypeIdx() != 0) {
				 bdIdx = String.valueOf(fileInfo.getTypeIdx());
			}else {
				bdIdx = "sc_board_idx.currval";
			}
			String sql = "insert into tb_file" + 
					"(f_idx, type_idx, type, origin_file_name," + 
					" rename_file_name, save_path)" + 
					"values(" + 
					"sc_file_idx.nextval, " + bdIdx + 
					",?,?,?,?)"; 
			
			PreparedStatement pstm = null;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, Code.UPLOAD_TYPE_BOARD.VALUE);
				pstm.setString(2, fileInfo.getOriginFileName());
				pstm.setString(3, fileInfo.getRenameFileName());
				pstm.setString(4, fileInfo.getSavePath());
				res = pstm.executeUpdate();
			} finally {
				jdt.close(pstm);
			}
			
			return res;
		}
		
		//게시글 목록
		public List<Board> selectBoardList(Connection conn, Paging page) throws SQLException{
			List<Board> result = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			String sql = "select" + 
					" bd_idx, user_id, reg_date, title " +  
					" from(" + 
					" select rownum rnum, n1.*" + 
					" from(" + 
					" select * " + 
					" from tb_Board" + 
					" order by bd_idx desc" + 
					" ) n1" + 
					" )where rnum between ? and ?";
			try {
				result = new ArrayList<Board>();
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, page.getStart());
				pstm.setInt(2, page.getEnd());
				
				rs = pstm.executeQuery();
				
				while(rs.next()) {
					Board Board = new Board();
					Board.setBdIdx(rs.getString(1));
					Board.setUserId(rs.getString(2));
					Board.setRegDate(rs.getDate(3));
					Board.setTitle(rs.getString(4));
					
					result.add(Board);
				}
			}finally {
				jdt.close(rs, pstm);
			}
			return result;
		}
		
		//전체 게시글 숫자 반환
		public int selectContentCnt(Connection conn) throws SQLException {
			int result = 0;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			String sql = "select count(*) from tb_Board";
			try {
				pstm = conn.prepareStatement(sql);
				rs = pstm.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}
			}finally {
				jdt.close(rs, pstm);
			}
			return result;
		}
		
		//게시글 상세
		public Board selectBoardDetail(Connection conn,String bdIdx) throws SQLException {
			Board Board = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			String sql = " select" + 
					" bd_idx, user_id, reg_date," + 
					" title, content" + 
					" from tb_Board" + 
					" where bd_idx =?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				
				rs = pstm.executeQuery();
				
				while(rs.next()) {
					Board = new Board();
					Board.setBdIdx(rs.getString(1));
					Board.setUserId(rs.getString(2));
					Board.setRegDate(rs.getDate(3));
					Board.setTitle(rs.getString(4));
					Board.setContent(rs.getString(5));
				}
			}finally {
				jdt.close(rs, pstm);
			}
			return Board;
		}
		
		//게시글 파일 정보
		public List<FileVo> selectFileWithBoard(Connection conn, String bdIdx) throws SQLException{
			List<FileVo> result = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			String sql = " select" + 
					" *" + 
					" from tb_file" + 
					" where type_idx = ?"
					+ " and type = ? ";
					
			try {
				result = new ArrayList<FileVo>();
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				pstm.setString(2, Code.UPLOAD_TYPE_BOARD.VALUE);
				rs = pstm.executeQuery();
				
				while(rs.next()) {
					FileVo fileVo = new FileVo();
					fileVo.setOriginFileName(rs.getString("origin_file_name"));
					fileVo.setRenameFileName(rs.getString("rename_file_name"));
					fileVo.setSavePath(rs.getString("SAVE_PATH"));
					fileVo.setTypeIdx(rs.getInt("type_idx"));
					fileVo.setfIdx(rs.getString("f_idx"));
					result.add(fileVo);
				}
			}finally {
				jdt.close(rs, pstm);
			}
			return result;
		}
		
		//파일 인덱스로 파일정보 반환하기
		public FileVo selectFileWithFidx(Connection conn, String fIdx) throws SQLException{
			FileVo fileVo = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			String sql = " select" + 
					" *" + 
					" from tb_file" + 
					" where f_idx = ?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, fIdx);
				rs = pstm.executeQuery();
				
				if(rs.next()) {
					fileVo = new FileVo();
					fileVo.setOriginFileName(rs.getString("origin_file_name"));
					fileVo.setRenameFileName(rs.getString("rename_file_name"));
					fileVo.setSavePath(rs.getString("SAVE_PATH"));
					fileVo.setTypeIdx(rs.getInt("type_idx"));
					fileVo.setfIdx(rs.getString("f_idx"));
				}
			}finally {
				jdt.close(rs, pstm);
			}
			return fileVo;
		}
		
		//파일 인덱스로 파일정보 삭제하기
		public int deleteFileWithFIdx(Connection conn,String fIdx) throws SQLException {
			int res = 0;
			String sql = " delete from tb_file where f_idx = ?"; 
			
			PreparedStatement pstm = null;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, fIdx);
				res = pstm.executeUpdate();
			}finally {
				jdt.close(pstm);
			}
			
			return res;
		}
		
		//파일테이블에서 해당 게시글번호의 파일 삭제
		public int deleteFileWithBoard(Connection conn, String bdIdx) throws SQLException{
			int res = 0;
			String sql = "delete from tb_file where type_idx = ? and type = ?"; 
			
			PreparedStatement pstm = null;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				pstm.setString(2, Code.UPLOAD_TYPE_BOARD.VALUE);
				res = pstm.executeUpdate();
			}finally {
				jdt.close(pstm);
			}
			
			return res;
		}
		
		//게시글 수정
		public int updateBoard(Connection conn, Board Board) throws SQLException {
			int res = 0;
			String sql = " update tb_Board "
					+ " set title = ?"
					+ " , content = ? "
					+ " where bd_idx = ?"; 
			
			PreparedStatement pstm = null;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, Board.getTitle());
				pstm.setString(2, Board.getContent());
				pstm.setInt(3, Board.getBdIdx());
				res = pstm.executeUpdate();
			}finally {
				jdt.close(pstm);
			}
			
			return res;
		}
		
		//게시글 삭제
		public int deleteBoard(Connection conn,String bdIdx) throws SQLException{
			int res = 0;
			String sql = "delete from tb_Board where bd_idx = ?"; 
			
			PreparedStatement pstm = null;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, bdIdx);
				res = pstm.executeUpdate();
			}finally {
				jdt.close(pstm);
			}
			
			return res;
		}

}
