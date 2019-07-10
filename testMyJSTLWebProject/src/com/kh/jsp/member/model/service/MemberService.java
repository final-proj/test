package com.kh.jsp.member.model.service;

import java.sql.Connection;

import com.kh.jsp.member.model.dao.MemberDao;
import com.kh.jsp.member.model.exception.MemberException;
import com.kh.jsp.member.model.vo.Member;
import static com.kh.jsp.common.JDBCTemplate.*;

// Service :
// 		Controller인 서블릿에서 전달한 정보를 
//		업무 수행로직(비즈니스 로직)에 따라
// 		가공하여 DAO에게 전달한는 역할
public class MemberService {
	// Service는 하나의 SQL을 담당하는 DAO 메소드들을
	// 더큰 하나의 서비스라는 개념으로 묶어 처리하는 
	// 비즈니스 로직(업무로직)을 담당하는 객체이다.
	// 따라서 DB의 트랜잭션에 대한 TCL(commit/rollback)처리를
	// 직접 담당해야한다.
	
	private Connection con;
	private MemberDao mDao = new MemberDao();
	
	
	// 회원 가입을 위한 메소드
	public int insertMember(Member m) throws MemberException {
		
		con = getConnection();
		int result = mDao.insertMember(con, m);
		
		if(result > 0 ) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public Member selectMember(Member m) throws MemberException {
		
		con = getConnection();
		
		Member result = mDao.selectMember(con, m);
		
		
		close(con);
		
		if(result == null) throw new MemberException("회원 아이디나 비밀번호가 올바르지 않습니다.");
		
		
		return result;
	}
	
	public int updateMember(Member m) throws MemberException {
		
		con = getConnection();
		
		int result = mDao.updateMember(con, m);
		
		if(result > 0 ) {
			commit(con);
			System.out.println("커밋 완");
		}
		else {
			rollback(con);
			System.out.println("롤백");
		}
		System.out.println("up : " +result);
		close(con);
		
		return result;
	}

	public int deleteMember(String userId) throws MemberException {
		
		con = getConnection();
		int result = mDao.deleteMember(con, userId);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
		
	}

	public int idDupCheck(String id) {
		con = getConnection();
		
		int result = mDao.idDupCheck(con, id);
		
		close(con);
		
		return result;
	}

}
