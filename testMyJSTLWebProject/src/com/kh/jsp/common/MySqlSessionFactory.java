package com.kh.jsp.common;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionFactory {
	// 마이바티스(config) 설정 파일을 읽어와서
	// DB와 Java를 연결하는 Connection 객체를
	// 생성하는 클래스입니다.

	private static SqlSessionFactory factory;

	public MySqlSessionFactory() {
	}

	public static SqlSessionFactory getSqlSessionFactory() {

		try {
			
			factory = new SqlSessionFactoryBuilder().build(

					Resources.getResourceAsStream("/config/mybatis/mybatis-config.xml")

			);

		} catch (IOException e) {

			e.printStackTrace();

		}

		return factory;
	}

}
