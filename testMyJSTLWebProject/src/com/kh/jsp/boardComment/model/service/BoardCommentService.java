package com.kh.jsp.boardComment.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.jsp.boardComment.model.dao.BoardCommentDao;
import com.kh.jsp.boardComment.model.vo.BoardComment;
import static com.kh.jsp.common.JDBCTemplate.*;

public class BoardCommentService {
	
	private BoardCommentDao bcDao = new BoardCommentDao();

	public int insertComment(BoardComment bco) {
		Connection con = getConnection();
		
		int result = bcDao.insetComment(con, bco);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		
		return result;
	}

	public ArrayList<BoardComment> selectList(int bno) {
		Connection con = getConnection();
		
		ArrayList<BoardComment> clist = bcDao.selectList(con, bno);
		
		close(con);
		
		return clist;
	}

	public int deleteComment(int cno) {
		Connection con = getConnection();
		
		int result = bcDao.deleteComment(con, cno);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public int updateComment(BoardComment bc) {
		Connection con = getConnection();
		
		int result = bcDao.updateComment(con, bc);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

	public int btypeBoard(int bno) {
		Connection con = getConnection();
		
		int result = bcDao.btypeBoard(con, bno);
		
		if(result > 0) commit(con);
		else rollback(con);
		
		close(con);
		
		return result;
	}

}
