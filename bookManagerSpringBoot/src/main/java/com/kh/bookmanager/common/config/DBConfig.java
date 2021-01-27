package com.kh.bookmanager.common.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class DBConfig {
	
	@Bean
	public OracleDataSource oracleDataSource() throws SQLException, IOException {
		OracleDataSource dataSource = new OracleDataSource();
		// Wallet_BM의 경로를 구하기 위해 클래스가 컴파일 되어 저장되는 경로를 구한다.
		// '/'로 시작하기 때문에 제일 첫 '/'를 제거 해준다.
		String walletPath = this.getClass().getResource("/").getPath().replaceFirst("/", "") + "Wallet_BM";
		dataSource.setURL("jdbc:oracle:thin:@bm_high?TNS_ADMIN="+walletPath);
		Properties prop = new Properties();
		prop.setProperty("user", "admin");
		prop.setProperty("password", "1256812gk!A!");
		dataSource.setConnectionProperties(prop);
		return dataSource;
	}
}
