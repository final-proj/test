package com.kh.jsp.board.model.dao;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.jsp.board.model.exception.BoardException;
import com.kh.jsp.board.model.vo.Board;
import com.kh.jsp.member.model.exception.MemberException;

import static com.kh.jsp.common.JDBCTemplate.*;

public class BoardDao {

	private Properties prop;

	public BoardDao() {
		prop = new Properties();

		String filePath = BoardDao.class.getResource("/config/board-query.properties").getPath();

		try {
			prop.load(new FileReader(filePath));

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public ArrayList<Board> selectList(Connection con, int currentPage, int limit) throws BoardException {
		ArrayList<Board> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectList");

		try {

			pstmt = con.prepareStatement(sql);

			// 1. 마지막 행의 번호
			// 2. 첫 행의 번호
			// 1 --> 1, 2 --> 11
			int startRow = (currentPage - 1) * limit + 1;

			int endRow = startRow + limit - 1;

			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);

			rset = pstmt.executeQuery();

			list = new ArrayList<Board>();

			while (rset.next()) {

				Board b = new Board();

				b.setBno(rset.getInt("BNO"));
				b.setBtype(rset.getInt("BTYPE"));
				b.setBtitle(rset.getString("BTITLE"));
				b.setBcontent(rset.getString("BCONTENT"));
				b.setBwriter(rset.getString("USERNAME"));
				b.setBcount(rset.getInt("BCOUNT"));
				b.setBdate(rset.getDate("BDATE"));
				b.setBoardfile(rset.getString("BOARDFILE"));

				list.add(b);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int getListCount(Connection con) throws BoardException {

		// 총 게시글 수
		int listCount = 0;

		Statement stmt = null;

		ResultSet rset = null;

		String sql = prop.getProperty("listCount");

		try {

			stmt = con.createStatement();

			rset = stmt.executeQuery(sql);

			if (rset.next()) {

				listCount = rset.getInt(1);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close(rset);
			close(stmt);
		}

		return listCount;
	}

	public Board selectOne(Connection con, int bno) throws BoardException {

		Board b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectOne");

		try {

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, bno);

			rset = pstmt.executeQuery();

			if (rset.next()) {

				b = new Board();

				b.setBno(bno);
				b.setBtype(1);
				b.setBtitle(rset.getString("BTITLE"));
				b.setBcontent(rset.getString("BCONTENT"));
				b.setBwriter(rset.getString("USERNAME"));
				b.setBwriterId(rset.getString("BWRITER"));
				b.setBcount(rset.getInt("BCOUNT"));
				b.setBoardfile(rset.getString("BOARDFILE"));
				b.setBdate(rset.getDate("BDATE"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new BoardException(e.getMessage());
			
		} finally {
			close(rset);
			close(pstmt);
		}

		return b;
	}

	public int insertBoard(Connection con, Board b) throws BoardException {
		int result = 0;
		PreparedStatement pstmt = null;

		String sql = prop.getProperty("insertBoard");

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, b.getBtitle());
			pstmt.setString(2, b.getBcontent());
			pstmt.setString(3, b.getBwriter());
			pstmt.setString(4, b.getBoardfile());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateReadCount(Connection con, int bno) throws BoardException {

		int result = 0;

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateReadCount");

		try {

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, bno);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			close(pstmt);

		}

		return result;
	}

	public ArrayList<Board> searchBoard(Connection con, String category, String keyword, int currentPage, int limit)
			throws BoardException {
		ArrayList<Board> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = null;

		switch (category) {
		case "writer":
			sql = prop.getProperty("searchWriterBoard");
			break;
		case "title":
			sql = prop.getProperty("searchTitleBoard");
			break;
		case "content":
			sql = prop.getProperty("searchContentBoard");
			break;
		}

		try {

			pstmt = con.prepareStatement(sql);

			int startRow = (currentPage - 1) * limit + 1;

			int endRow = startRow + limit - 1;

			if (category.equals("writer")) {
				pstmt.setString(3, keyword);
				pstmt.setInt(1, endRow);
				pstmt.setInt(2, startRow);
			} else {

				pstmt.setString(1, keyword);
				pstmt.setInt(2, endRow);
				pstmt.setInt(3, startRow);

			}

			rset = pstmt.executeQuery();

			list = new ArrayList<Board>();

			System.out.println(keyword);

			while (rset.next()) {

				Board b = new Board();

				b.setBno(rset.getInt("BNO"));
				b.setBtype(rset.getInt("BTYPE"));
				b.setBtitle(rset.getString("BTITLE"));
				b.setBcontent(rset.getString("BCONTENT"));
				b.setBwriter(rset.getString("USERNAME"));
				b.setBcount(rset.getInt("BCOUNT"));
				b.setBdate(rset.getDate("BDATE"));
				b.setBoardfile(rset.getString("BOARDFILE"));

				list.add(b);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int updateBoard(Connection con, Board b) throws BoardException {

		int result = 0;

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");

		try {

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, b.getBtitle());
			pstmt.setString(2, b.getBcontent());
			pstmt.setString(3, b.getBoardfile());
			pstmt.setInt(4, b.getBno());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			close(pstmt);

		}

		return result;
	}

	public int deleteBoard(Connection con, int bno) throws BoardException {

		int result = 0;

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteBoard");

		try {

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, bno);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			close(pstmt);
		}

		return result;
	}

	public ArrayList<Board> selectTop5(Connection con) {

		ArrayList<Board> list = null;
		Statement stmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectTop5");

		try {

			stmt = con.createStatement();

			rset = stmt.executeQuery(sql);

			list = new ArrayList<Board>();

			while (rset.next()) {

				Board b = new Board();

				b.setBno(rset.getInt("BNO"));
				b.setBtype(rset.getInt("BTYPE"));
				b.setBtitle(rset.getString("BTITLE"));
				b.setBcontent(rset.getString("BCONTENT"));
				b.setBwriter(rset.getString("USERNAME"));
				b.setBcount(rset.getInt("BCOUNT"));
				b.setBdate(rset.getDate("BDATE"));
				b.setBoardfile(rset.getString("BOARDFILE"));

				list.add(b);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return list;
	}

}
