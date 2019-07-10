package com.kh.jsp.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.jsp.notice.model.dao.NoticeDao;
import com.kh.jsp.notice.model.exception.NoticeException;
import com.kh.jsp.notice.model.vo.Notice;
import static com.kh.jsp.common.JDBCTemplate.*;

public class NoticeService {
	
	private NoticeDao nDao = new NoticeDao();

	public ArrayList<Notice> selectList() throws NoticeException {
		
		Connection con = getConnection();
		ArrayList<Notice> list = nDao.selectList(con);

		close(con);
		
		return list;
	}

	public int insertNotice(Notice n) throws NoticeException {
		Connection con = getConnection();
		
		int result = nDao.insertNotice(con, n);
		
		/*
		 *  0 : 실행한 행의 개수 없음
		 *  1 이상 : n 개의 행 실행
		 *  -1 : 실행 중 에러 발생
		 */
		
		if(result >= 1) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	// View -> Controller -> Service -> Dao <-> DB
	// Dao -> Service -> Controller(Servlet) -> View

	
	public Notice selectOne(int nno) throws NoticeException {
		Connection con = getConnection();
		
		Notice n = nDao.selectOne(con, nno);
		
		// 게시글 상세보기를 통해 1회 조회할 때 
		// 2가지 기능이 실행된다.
		// 1. nno에 해당하는 게시글 내용을 가져오는 것. (SELECT)
		// 2. 게시글 내용이 성공적으로 불러와 졌다면 조회수가 1 증가해야 한다. (UPDATE)
		
		if( n != null ) {
			
			int result = nDao.updateReadCount(con, nno);
			
			if( result > 0) commit(con);
			else rollback(con);
			
		}
		
		close(con);
		
		
		return n;
	}

	public Notice updateView(int nno) throws NoticeException {
		
		Connection con = getConnection();
		
		Notice n = nDao.selectOne(con, nno);
		
		close(con);
		
		return n;
	}

	public int updateNotice(Notice n) throws NoticeException {
		
		Connection con = getConnection();
		
		int result = nDao.updateNotice(con, n);
		
		if( result > 0 ) commit(con);
		else rollback(con);
		
		close(con);
		
		
		return result;
	}

	public int deleteNotice(int nno) throws NoticeException {
		
		Connection con = getConnection();
		
		int result = nDao.deleteNotice(con, nno);
		
		if( result > 0 ) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public ArrayList<Notice> searchNotice(String category, String keyword) throws NoticeException {
		Connection con = getConnection();
		ArrayList<Notice> list = null;
		
		// if(condition.length() > 0 ) list = nDao.searchNotice(con, condition, keyword);
		// else list = nDao.selectList(con);
		
		list = (category.length() > 0) ? 
				nDao.searchNotice(con, category, keyword) : nDao.selectList(con); 
		
		return list;
	}
	

}
