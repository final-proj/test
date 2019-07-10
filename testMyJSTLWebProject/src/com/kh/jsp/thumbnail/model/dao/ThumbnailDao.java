package com.kh.jsp.thumbnail.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.kh.jsp.thumbnail.model.vo.Attachment;
import com.kh.jsp.thumbnail.model.vo.Thumbnail;

import static com.kh.jsp.common.JDBCTemplate.*;

public class ThumbnailDao {
	
	private Properties prop = new Properties();
	
	public ThumbnailDao() {
		
		String filePath = ThumbnailDao.class.getResource("/config/thumbnail-query.properties").getPath();
		
		try {
			
			prop.load(new FileReader(filePath));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
			
		}
	}

	public ArrayList<Thumbnail> selectList(Connection con) {
		
		ArrayList<Thumbnail> list = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			
			stmt = con.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			list = new ArrayList<Thumbnail>();
			
			while(rset.next()) {
				
				Thumbnail tn = new Thumbnail();
				
				tn.setBno(rset.getInt("BNO"));
				tn.setBtitle(rset.getString("BTITLE"));
				tn.setBcontent(rset.getString("BCONTENT"));
				tn.setBwriter(rset.getString("USERNAME"));
				tn.setBcount(rset.getInt("BCOUNT"));
				tn.setBdate(rset.getDate("BDATE"));
				tn.setBoardfile(rset.getString("CHANGENAME"));
				
				list.add(tn);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(rset);
			close(stmt);
			
		}
		
		
		
		return list;
	}

	public int insertThumbnail(Connection con, Thumbnail t) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("insertThumbnail");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, t.getBtitle());
			pstmt.setString(2, t.getBcontent());
			pstmt.setString(3, t.getBwriter());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		} finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

	public int selectCurrentBno(Connection con) {
		
		Statement stmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String sql = prop.getProperty("selectLastBno");
		
		try {
			
			stmt = con.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) result = rset.getInt(1);
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		
		return result;
	}

	public int insertAttachment(Connection con, ArrayList<Attachment> list) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("insertAttachment");
		
		try {
			
			for( int i = 0; i < list.size() ; i++ ) {
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, list.get(i).getBno());
				pstmt.setString(2, list.get(i).getOriginname());
				pstmt.setString(3, list.get(i).getChangename());
				pstmt.setString(4, list.get(i).getFilepath());
				
				// 첫번째로 들어가는 데이터는 대표 이미지로,
				// 나머지는 서브 이미지로 레벨을 결정한다.
				
				int level = 0;
				if(i != 0 ) level = 1;
				
				pstmt.setInt(5, level);
				
				result += pstmt.executeUpdate();
				
			}
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			close(pstmt);
		
		}

		
		return result;
	}

	public HashMap<String, Object> selectThumbnailMap(Connection con, int bno) {
		// 객체 준비하기
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Attachment> list = null;
		
		Thumbnail t = null;
		
		HashMap<String, Object> hmap = null;
		
		String sql = prop.getProperty("selectOne");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Attachment>();
			
			System.out.println("bno : " + bno);
			
			while(rset.next()) {
				
				t = new Thumbnail();
				
				t.setBno(rset.getInt("BNO"));
				t.setBtitle(rset.getString("BTITLE"));
				t.setBcontent(rset.getString("BCONTENT"));
				t.setBwriter(rset.getString("USERNAME"));
				t.setBwriterId(rset.getString("BWRITER"));
				t.setBcount(rset.getInt("BCOUNT"));
				t.setBdate(rset.getDate("BDATE"));
				
				System.out.println("thumb : " + t);
				
				Attachment att = new Attachment();
				
				att.setFno(rset.getInt("FNO"));
				att.setOriginname(rset.getString("ORIGINNAME"));
				att.setChangename(rset.getString("CHANGENAME"));
				att.setFilepath(rset.getString("FILEPATH"));
				att.setUploaddate(rset.getDate("UPLOADDATE"));
				att.setFlevel(rset.getInt("FLEVEL"));
				
				list.add(att);
				
				System.out.println("att : " + att);
			}
			
			hmap = new HashMap<String, Object>();
			
			hmap.put("thumbnail", t);
			hmap.put("attachment", list);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			close(rset);
			close(pstmt);
			
		}
		
		
		return hmap;
	}

	public int updateThumbnail(Connection con, Thumbnail t) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("updateThumbnail");
		
		try {
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, t.getBtitle());
			pstmt.setString(2, t.getBcontent());
			pstmt.setInt(3, t.getBno());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}

		
		
		return result;
	}

	public int updateAttachment(Connection con, ArrayList<Attachment> list) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("updateAttachment");
		
		try {
			
			for(int i = 0 ; i < list.size(); i++) {
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, list.get(i).getOriginname());
				pstmt.setString(2, list.get(i).getChangename());
				pstmt.setInt(3, list.get(i).getFno());
				
				result += pstmt.executeUpdate();
				
			}
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			close(pstmt);
			
		}
		
		
		
		return result;
	}
	
	

}
