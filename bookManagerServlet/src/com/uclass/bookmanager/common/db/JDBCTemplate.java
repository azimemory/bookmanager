package com.uclass.bookmanager.common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;

public class JDBCTemplate {
	private final static String DB_URL= "jdbc:oracle:thin:@bm_high?TNS_ADMIN=C:\\CODE\\Wallet_BM";
	private final static String DB_USER = "admin";
	private final static String DB_PASSWORD = "1256812gk!A!";
	
	//외부에서의 생성을 막는다.
	private JDBCTemplate() {
		
	}
	
	//내부클래스에 있으면 static이더라도 초기화 되지 않는다.
	//getInstance 메서드가 호출될 때 까지 JDBCTemplate 객체는 생성되지 않는다.
	private static class JdbcTemplateHolder{
		
	  //상수에 JDBCTemplate객체를 담는다.	
	  //상수이기 때문에 한번 초기화 된 이후로는 다시 값을 담지 않는다.
	  //한번 초기화 이후에는 = new JDBCTemplate(); 코드가 실행되지 않음.
	  //static이기 때문에 instance가 메모리에서 내려가지 않는다.
	  //가비지컬렉터의 대상이 아니게 된다.
	  private static final JDBCTemplate instance = new JDBCTemplate();
	}
	
	public static JDBCTemplate getInstance() {
		return JdbcTemplateHolder.instance;
	}
	
	public Connection getConnection() {
		 Properties info = new Properties();     
	     info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
	     info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);  
	   
	     OracleDataSource ods = null;
	     OracleConnection connection = null;
	     
		try {
			ods = new OracleDataSource();
			ods.setURL(DB_URL);    
		    ods.setConnectionProperties(info);
		    // With AutoCloseable, the connection is closed automatically.
		    connection = (OracleConnection) ods.getConnection(); 
		    connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}
	
	public void close(Connection conn) {
		
		try {
			if(conn != null)
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//preparedStatement는 statement의 후손 클래스
	//같이 처리가 가능하다.
	public void close(Statement stmt) {
		
		try {
			if(stmt != null)
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rs) {
		
		try {
			if(rs != null)
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rs, Statement stmt) {
		
		try {
			if(rs != null)
			rs.close();
			if(stmt != null)
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rs, Statement stmt, Connection con) {
		
		try {
			if(con != null)
			con.close();
			if(rs != null)
			rs.close();
			if(stmt != null)
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void commit(Connection conn) {
		
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rollback(Connection conn) {
		
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
