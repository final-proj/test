package com.kh.jsp.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.jsp.board.model.dao.BoardDao;
import com.kh.jsp.board.model.exception.BoardException;
import com.kh.jsp.board.model.vo.Board;
import static com.kh.jsp.common.JDBCTemplate.*;

public class BoardService {

	private BoardDao bDao = new BoardDao();
	
	public ArrayList<Board> selectList(int currentPage, int limit) throws BoardException {
		Connection con = getConnection();
		
		ArrayList<Board> list = bDao.selectList(con, currentPage, limit);
		
		close(con);
		
		return list;
	}

	public int getListCount() throws BoardException {
		Connection con = getConnection();
		int listCount = bDao.getListCount(con);
		
		close(con);
		return listCount;
	}

	public Board selectOne(int bno) throws BoardException {
		Connection con = getConnection();
		
		Board b = bDao.selectOne(con, bno);
		
		if( b != null ) {
			
			int result = bDao.updateReadCount(con, bno);
			
			if( result > 0) commit(con);
			else rollback(con);
			
		}
		
		close(con);
		
		return b;
	}

	public int insertBoard(Board b) throws BoardException {
		Connection con = getConnection();
		
		int result = bDao.insertBoard(con, b);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public ArrayList<Board> searchBoard(String category, String keyword, 
											int currentPage, int limit) throws BoardException {
		Connection con = getConnection();
		
		ArrayList<Board> list = null;
		
		
		
		list = (category.length() > 0) ? 
				bDao.searchBoard(con, category, keyword, currentPage, limit) :
															bDao.selectList(con, currentPage, limit);
		
		
		
		return list;
	}

	public Board updateView(int bno) throws BoardException {
		Connection con = getConnection();
		
		Board b = bDao.selectOne(con, bno);
		
		close(con);
		
		
		return b;
	}

	public int updateBoard(Board b) throws BoardException {
		Connection con = getConnection();
		
		int result = bDao.updateBoard(con, b);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public int deleteBoard(int bno) throws BoardException {
		Connection con = getConnection();
		
		int result = bDao.deleteBoard(con, bno);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public ArrayList<Board> selectTop5() {
		Connection con = getConnection();
		
		ArrayList<Board> list = bDao.selectTop5(con);
		
		close(con);
		
		return list;
	}

}
