package com.uclass.bookmanager.rent.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.uclass.bookmanager.rent.model.vo.Rent;
import com.uclass.bookmanager.common.db.JDBCTemplate;
import com.uclass.bookmanager.common.util.page.Paging;

public class RentBookDao {
	
JDBCTemplate jdt = JDBCTemplate.getInstance();

	public int totalCountInRent(Connection conn, String id) throws SQLException {
		
		int result = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select count(*) "
				+ "from tb_rent_master "
				+ "where user_id = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		}finally {
			jdt.close(rs, pstm);
		}
		
		return result;
	}

	
	public List<Rent> selectRentinfo(Connection conn, String id, Paging paging) throws SQLException{
		List<Rent> result = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from" + 
				"(select rownum rnum, a.* from( " + 
				"    select *  " + 
				"    from tb_rent_master rm  " + 
				"    where user_id = ? " + 
				"    order by rm_idx desc) a" + 
				") where rnum between ? and ?";
		try {
			result = new ArrayList<Rent>();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setInt(2, paging.getStart());
			pstm.setInt(3, paging.getEnd());
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Rent rent = new Rent();
				rent.setRmIdx(rs.getInt(2));
				rent.setUserId(rs.getString(3));
				rent.setRegDate(rs.getDate(4));
				rent.setIsReturn(rs.getString(5));
				rent.setTitle(rs.getString(6));
				rent.setRentBookCnt(rs.getInt(7));
				result.add(rent);
			}
		}finally {
			jdt.close(rs, pstm);
		}
		return result;
	}
	
	public List<Map<String,Object>> selectRenbooktinfo(Connection con, String searchType, String rentIdx) throws SQLException{
		List<Map<String,Object>> result = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " select "
				+ " rb.rm_Idx, rb.rb_Idx, c.Info, b.title, b.author, b.isbn,"
				+ " rb.reg_date, rb.return_date, rb.EXTENTION_CNT, rb.state "
				+ " from tb_rent_book rb"
				+ " inner join tb_book b using(b_idx) "
				+ " inner join tb_code c on(c.code = b.CATEGORY)"			
				+ " where ";
		switch (searchType) {
			case "rmIdx" : sql += " rb.rm_Idx = ? ";
				break;
			case "rbIdx" : sql += " rb.rb_Idx = ? ";
				break;
		}
		
		try {
			result = new ArrayList<Map<String,Object>>();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, rentIdx);
			rs = pstm.executeQuery();
			while(rs.next()) {
				Map<String,Object> commandMap = new LinkedHashMap<>();
				commandMap.put("rmIdx",rs.getInt(1));
				commandMap.put("rbIdx",rs.getInt(2));
				commandMap.put("category",rs.getString(3));
				commandMap.put("title",rs.getString(4));
				commandMap.put("author",rs.getString(5));
				commandMap.put("isbn",rs.getString(6));
				commandMap.put("regDate",rs.getDate(7));
				commandMap.put("returnDate",rs.getDate(8));
				commandMap.put("extentionCnt",rs.getInt(9));
				commandMap.put("state",rs.getString(10));
				result.add(commandMap);
			}
		}finally {
			jdt.close(rs, pstm);
		}
		
		return result;
	}

	public int insertRentInfo(Connection con, Rent rent) throws SQLException{
		int result = 0;
		PreparedStatement pstm = null;
		String sql = "insert into tb_rent_master "
				+ "(rm_idx,user_id,title,rent_book_cnt)"
				+ " values(SC_RM_IDX.nextval,?,?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, rent.getUserId());
			pstm.setString(2, rent.getTitle());
			pstm.setInt(3, rent.getRentBookCnt());
			result = pstm.executeUpdate();
		}finally {
			jdt.close(pstm);
		}
		
		return result;
	}

	public boolean insertRentBookInfo(Connection con, int bidx) throws SQLException  {
		boolean isSuccess = false;
		CallableStatement cstm = null;
		String sql = "{call SP_RENT_INSERT(?,?)}";
		try {
			cstm = con.prepareCall(sql);
			cstm.setInt(1, bidx);
			cstm.registerOutParameter(2, java.sql.Types.NUMERIC);
			cstm.execute();
			int status = cstm.getInt(2);
			isSuccess = true;
		}finally {
			jdt.close(cstm);
		}
		
		return isSuccess;
	}

	public boolean updateReturnRentBook(Connection con, Rent rent) throws SQLException {
		boolean isSuccess = false;
		CallableStatement cstm = null;
		String sql = "{call SP_RENT_RETURN(?,?,?,?)}";
		try {
			cstm = con.prepareCall(sql);
			cstm.setInt(1, rent.getRmIdx());
			cstm.setInt(2, rent.getRbIdx());	
			cstm.setString(3, rent.getUserId());	
			cstm.registerOutParameter(4, java.sql.Types.NUMERIC);
			cstm.executeQuery();
			//int res = cstm.getInt(4);
			isSuccess = true;
		}finally {
			jdt.close(cstm);
		}
		return isSuccess;
	}
	
	public boolean updateExtendRentState(Connection con, String rmIdx, String rbIdx) throws SQLException {
		boolean isSuccess = false;
		CallableStatement cstm = null;
		String sql = "{call SP_RENT_EXTEND(?,?,?)}";
		try {
			cstm = con.prepareCall(sql);
			cstm.setString(1, rmIdx);
			cstm.setString(2, rbIdx);		
			cstm.registerOutParameter(3, java.sql.Types.NUMERIC);
			cstm.executeQuery();
			int res = cstm.getInt(3);
			isSuccess = true;
		}finally {
			jdt.close(cstm);
		}
		return isSuccess;
	}

	//프로시저 적용 전	
	public int updateExtendRentBook(Connection con, String rbidx) throws SQLException{
		int result = 0;
		PreparedStatement pstm = null;
		String sql = "update tb_rent_book "
				+ "set state = R003"
				+ ", EXTENTION_CNT = EXTENTION_CNT+1 "
				+ ", return_date = return_date + 7"
				+ " where rb_idx = " + rbidx;
		
		try {
			pstm = con.prepareStatement(sql);
			result = pstm.executeUpdate();
		}finally {
			jdt.close(pstm);
		}
		return result;
	}
	
	//프로시저 적용 전	
	public int insertUpdateHistory(Connection con, String rb_idx) throws SQLException{
		
		PreparedStatement pstm = null;
		String sql = "insert into tb_rent_history " + 
				"select SC_RH_IDX.NEXTVAL, a.*" + 
				"from (select rm_idx, rb_idx, b_idx, sysdate, state" + 
				"from tb_rent_book where rb_idx = ?) a";
		
		int result = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, rb_idx);
			result = pstm.executeUpdate();
		}finally {
			jdt.close(pstm);
		}
		return result;
	}
	
	//연체도서 검사
	public int selectLateCnt(Connection conn, String userId) throws SQLException{
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select count(*) from tb_rent_book where user_id = ? and state = 'RE02'";
		int lateCnt = 0;
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				lateCnt = rs.getInt(1);
			}
		}finally {
			jdt.close(rs, pstm);
		}
		
		return lateCnt;
	}
}
