package com.kh.jsp.notice.model.dao;

import static com.kh.jsp.common.JDBCTemplate.close;

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

import com.kh.jsp.notice.model.exception.NoticeException;
import com.kh.jsp.notice.model.vo.Notice;

public class NoticeDao {
	
	private Properties prop;
	
	public NoticeDao() {
		prop = new Properties();
		
		String filePath = NoticeDao.class.getResource("/config/notice-query.properties").getPath();
		
		try {
			
			prop.load(new FileReader(filePath));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}

	public ArrayList<Notice> selectList(Connection con) throws NoticeException {
		
		ArrayList<Notice> list = null;
		
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			list = new ArrayList<Notice>();
			
			while(rset.next()) {
				Notice n = new Notice();
				
				n.setNno(rset.getInt(1));
				n.setNtitle(rset.getString("ntitle"));
				n.setNcontent(rset.getString("ncontent"));
				n.setNwriter(rset.getString("nwriter"));
				n.setNcount(rset.getInt("ncount"));
				n.setNdate(rset.getDate("ndate"));
				
				list.add(n);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	public int insertNotice(Connection con, Notice n) throws NoticeException {
		
		int result = 0; // SQL 의 결과
		
		String sql = prop.getProperty("insertNotice");
		
		PreparedStatement pstmt = null;
		
		try {
			// 미완성된 SQL 준비
			pstmt = con.prepareStatement(sql);
			
			// SQL의 ?를 채울 쿼리 완성
			pstmt.setString(1, n.getNtitle());
			pstmt.setString(2, n.getNcontent());
			pstmt.setString(3, n.getNwriter());
			pstmt.setDate(4, n.getNdate());
			
			// 결과 확인
			result = pstmt.executeUpdate();	
			
			// View -> Servlet -> Service -> Dao <-> DB !
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			close(pstmt);
			
		}
		
		
		
		return result;
	}

	public Notice selectOne(Connection con, int nno) throws NoticeException {
		
		Notice n = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectOne");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, nno);
			
			// INSERT, UPDATE, DELETE : executeUpdate()
			// SELECT : executeQuery();
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				n = new Notice();
				
				n.setNno(nno);
				n.setNtitle(rset.getString("ntitle"));
				n.setNcontent(rset.getString("ncontent"));
				n.setNwriter(rset.getString("nwriter"));
				n.setNcount(rset.getInt("ncount"));
				n.setNdate(rset.getDate("ndate"));
				
			}
			
			System.out.println("notice 확인 : " + n);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(rset);
			close(pstmt);
			
		}
		
		return n;
	}

	
	
	public int updateReadCount(Connection con, int nno) throws NoticeException {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateReadCount");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, nno);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		
		return result;
	}

	public int updateNotice(Connection con, Notice n) throws NoticeException {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateNotice");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, n.getNtitle());
			pstmt.setString(2, n.getNcontent());
			pstmt.setInt(3, n.getNno());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

	public int deleteNotice(Connection con, int nno) throws NoticeException {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteNotice");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, nno);
			
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

	public ArrayList<Notice> searchNotice(Connection con, String category, String keyword) throws NoticeException {
		ArrayList<Notice> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = null;
		
		switch(category) {
		case "writer" :
			sql = prop.getProperty("searchWriterNotice");
			break;
		case "title" :
			sql = prop.getProperty("searchTitleNotice");
			break;
		case "content" :
			sql = prop.getProperty("searchContentNotice");
			break;
		}
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Notice>();
			
			System.out.println(keyword);
			
			while(rset.next()){
				
				Notice n = new Notice();
				
				n.setNno(rset.getInt("nno"));
				n.setNtitle(rset.getString("ntitle"));
				n.setNcontent(rset.getString("ncontent"));
				n.setNwriter(rset.getString("nwriter"));
				n.setNcount(rset.getInt("ncount"));
				n.setNdate(rset.getDate("ndate"));
				
				list.add(n);
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		// 확인용 출력문
		for(Notice n : list) System.out.println(n);
		
		return list;
	}
	
	

}
