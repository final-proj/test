package com.kh.jsp.boardComment.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.jsp.boardComment.model.vo.BoardComment;
import static com.kh.jsp.common.JDBCTemplate.*;

public class BoardCommentDao {
	
	private Properties prop = new Properties();
	
	

	public BoardCommentDao() {
		String filePath = BoardCommentDao.class.getResource("/config/comment-query.properties")
				.getPath();
		
		try {
			
			prop.load(new FileReader(filePath));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}



	public int insetComment(Connection con, BoardComment bco) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertComment");
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bco.getBno());
			pstmt.setString(2, bco.getCcontent());
			pstmt.setString(3, bco.getCwriter());
			
			if(bco.getRefcno() > 0) {
				
				pstmt.setInt(4, bco.getRefcno());
				
			}else {
				
				pstmt.setNull(4, java.sql.Types.NULL);
				
			}
			
			pstmt.setInt(5, bco.getClevel());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}



	public ArrayList<BoardComment> selectList(Connection con, int bno) {
		
		ArrayList<BoardComment> clist = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rset = pstmt.executeQuery();
			
			clist = new ArrayList<BoardComment>();
			
			while(rset.next()) {
				BoardComment bco = new BoardComment();
				
				bco.setCno(rset.getInt("CNO"));
				bco.setBno(bno);
				bco.setCcontent(rset.getString("CCONTENT"));
				bco.setCwriter(rset.getString("USERNAME"));
				bco.setCwriterId(rset.getString("CWRITER"));
				bco.setCdate(rset.getDate("CDATE"));
				bco.setRefcno(rset.getInt("REF_CNO"));
				bco.setClevel(rset.getInt("CLEVEL"));
				
				clist.add(bco);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		
		
		return clist;
	}



	public int deleteComment(Connection con, int cno) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteComment");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cno);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}



	public int updateComment(Connection con, BoardComment bc) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateComment");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bc.getCcontent());
			pstmt.setInt(2, bc.getCno());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		return result;
	}



	public int btypeBoard(Connection con, int bno) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("boardType");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		return result;
	}

}
