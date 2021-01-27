package com.kh.bookmanager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import oracle.jdbc.pool.OracleDataSource;

@SpringBootApplication
public class BookManagerSpringBootApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BookManagerSpringBootApplication.class, args);
	}
}
