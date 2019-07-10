package com.kh.jsp.member.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static com.kh.jsp.common.JDBCTemplate.*;

import com.kh.jsp.common.JDBCTemplate;
import com.kh.jsp.member.model.exception.MemberException;
import com.kh.jsp.member.model.vo.Member;

// DAO(Data Access Object) :
// 		Service 로부터 받은 정보를 
//		실제 데이터베이스에 전달하여 CRUD를 
// 		수행하는 객체
public class MemberDao {
	// SQL을 별도로 보관하는 Properties 객체 생성하기
	private Properties prop;
	
	public MemberDao() {
		prop = new Properties();
		
		String filePath
			= MemberDao.class.getResource("/config/member-query.properties").getPath();
		
		try {
			
			prop.load(new FileReader(filePath));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
	

	// 회원 가입 메소드
	public int insertMember(Connection con, Member m) throws MemberException {
		
		// 결과 확인을 위한 변수 result 생성
		int result = 0;

		
		
		PreparedStatement pstmt = null;
		
		try {
			
			
			// 4. 쿼리문 작성하기
			String sql = prop.getProperty("insertMember");
			
			
			// 4-1. PreparedStatement 객체 생성하기
			//  pstmt는 실행 전에 반드시 쿼리 양식을 들고 있어야 한다.
			pstmt = con.prepareStatement(sql);
			
			
			// 4-2. 등록한 ? 에 해당하는 값 추가하기
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			
			
			// 5. 작성한 쿼리문을 실행시켜 결과 받기
			// select : ResultSet 객체
			// insert, update, delete : 실행된 행의 개수( int (-1 : ORA-ERROR 무조건 오라클 에러))
			result = pstmt.executeUpdate();
			
			
		
			
		}  catch (SQLException e) {
			
			// e.printStackTrace();
			throw new MemberException(e.getMessage());
			
		} finally {
			close(pstmt);
			
		}
		
		return result;
	}

	public Member selectMember(Connection con, Member m) throws MemberException {
		// 회원 정보를 조회하여 아이디와 비밀번호가 일치 할 경우
		// 해당 객체를 가져오는 메소드
		
		// select의 경우 SQL 실행 결과로 가져오는 값이
		// 숫자 자료형이 아닌 객체(ResultSet)를 반환한다.
		
		Member result = null; // 결과를 담을 객체
		
		PreparedStatement pstmt = null;
		ResultSet rset = null; // Select의 결과를 담은 객체
		
		try {
			
			
			
			
			// 5. SQL 작성하기
			String sql = prop.getProperty("selectMember");
			
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			
			
			
			// 6. 쿼리를 수행하고 그 결과 받아오기
			rset = pstmt.executeQuery();
			
			// 7. ResultSet에 담긴 객체를 vo 객체에 기록하기
			//    ResultSet은 쿼리 수행의 결과가 참이든 거짓이든 항상 값을 가진다.
			//    (어떻게 실행하든 반드시 머릿글을 포함하기 때문이다.)
			//    실행한 결과가 존재하는지 확인하기 위해서 rset.next() 메소드를 사용한다.
			if(rset.next()) {
				result = new Member();
				
				// 이미 존재하는 값 재활용하기
				result.setUserId(m.getUserId());
				result.setUserPwd(m.getUserPwd());
				
				// ResultSet으로부터 결과를 추출하는 방법
				// 1. 결과를 행의 순서대로 가져올 경우(1, 2, 3....)
				// 2. 컬럼명으로 직접 선언하여 가져오는 경우(대소문자를 가리지 않는다.)
				
				result.setUserName(rset.getString(3));
				result.setAge(rset.getInt("age"));
				result.setGender(rset.getString("GENDER"));
				result.setEmail(rset.getString("email"));
				result.setPhone(rset.getString("phone"));
				result.setAddress(rset.getString("address"));
				result.setHobby(rset.getString("hobby"));
				
			}
			
			
		} catch(Exception e) {
			
			// e.printStackTrace();
			throw new MemberException(e.getMessage());
			
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		
		return result;
	}
	
	
	public int updateMember(Connection con, Member m) throws MemberException {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		try {

			String sql = prop.getProperty("updateMember");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setInt(3, m.getAge());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getAddress());
			pstmt.setString(6, m.getHobby());
			pstmt.setString(7, m.getUserId());
			
			result = pstmt.executeUpdate();
			System.out.println(result);
			
		
		} catch ( SQLException e) {
			 
			// e.printStackTrace();
			throw new MemberException(e.getMessage());
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection con, String userId) throws MemberException {
		
		
		int result = 0;
		
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = prop.getProperty("deleteMember");
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			
			
		} catch ( SQLException e) {

			// e.printStackTrace();
			throw new MemberException(e.getMessage());
			
		} finally {
			
			close(pstmt);
			
		}
		
		return result;
	}


	public int idDupCheck(Connection con, String id) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = -1;
		
		
		String sql = prop.getProperty("idDupCheck");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				// -1 : 에러!
				// 0 : id 중복 사용자 없음
				// 1 : id 중복 사용자 있음
				result = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(rset);
			close(pstmt);
			
		}
		
		
		return result;
	}


	
	
}
