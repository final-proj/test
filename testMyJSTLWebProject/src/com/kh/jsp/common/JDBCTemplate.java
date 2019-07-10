package com.kh.jsp.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCTemplate {
	// JDBC 연결과 관련된 단일 메소드 작성
	// 		: 여러 DAO들 간의 공통적인 JDBC 연결 구문을 하나의 클래스 안의 메소드들로 통합하는 형태의
	//		클래스로, 중복되는 소스 코드를 최대한 배재하며 여러개의 객체를 하나로 만듦으로써 공유하여
	// 		사용할 수 있는 static 메소드의 형태로 구현할 수 있다.
	
	// Singleton design pattern
	// 여러 클래스가 공통되는 코드를 필요로 하거나 하나의 애플리케이션에서 단 하나의 클래스로
	// 기능들을 관리하고자 할 때 사용하는 코딩 패턴
	// 여러 메소드들을 static 으로 관리하는 클래스
	
	public static Connection getConnection() {
		
		Connection con = null;
		
		try {
			
			// 데이터 베이스 연결 방법 3가지
			
			// 1. 일반 연결
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			
//			con = DriverManager.getConnection("jdbc:orcle:thin:@localhost:1521:xe", "JSP", "JSP");
			
			
			// 2. Properties 사용하기
//			Properties prop = new Properties();
//			
//			// driver.properties 에 접근하기
//			String fileName = JDBCTemplate.class.getResource("/config/driver.properties").getPath();
//			
//			prop.load(new FileReader(fileName));
//			
//			String driver = prop.getProperty("dirver");
//			String url = prop.getProperty("url");
//			String username = prop.getProperty("username");
//			String password = prop.getProperty("password");
//			
//			Class.forName(driver);
//			
//			con = DriverManager.getConnection(url, username, password);
			
			
			
			// 3. XML 설정을 통한 JNDI 방식의 설정 불러오기
			// JNDI ( Java Naming Directory Interface )
			// - Spring 프레임워크의 방식과 유사하다.
			// context.xml 에 데이터 베이스 설정을 담아 일반 Properties 보다 좀 더 명확하게
			// 그리고 context 영역(애플리케이션 영역)에 DB 연결 설정을 직접 설정하여 
			// 서버 자체적으로 제어 함으로써 DB 연결에 대한 주도권을 JVM으로부터 일부 양도받는 방식의 설정방법
			
			// Context 객체 가져오기
			Context initContext = new InitialContext();
			
			DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/oraDB");
			
			con = ds.getConnection();
			
			con.setAutoCommit(false);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return con;
	}
	
	
	// 오버로딩 기술 적용!
	public static void close(Connection con) {
		
		try {
			if(con != null && !con.isClosed())
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 오버로딩
	public static void close(Statement stmt) {
		
		try {
			
			if(stmt != null && !stmt.isClosed())
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 오버로딩
	public static void close(ResultSet rset) {
		
			try {
				if(rset != null && !rset.isClosed())
					rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void commit(Connection con) {
		
		try {
			if(con != null && !con.isClosed())
				con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con) {
		
		try {
			if(con != null && !con.isClosed())
				con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
