package com.kh.jsp.thumbnail.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.kh.jsp.board.model.dao.BoardDao;
import com.kh.jsp.board.model.exception.BoardException;
import com.kh.jsp.thumbnail.model.dao.ThumbnailDao;
import com.kh.jsp.thumbnail.model.vo.Attachment;
import com.kh.jsp.thumbnail.model.vo.Thumbnail;
import static com.kh.jsp.common.JDBCTemplate.*;

public class ThumbnailService {
	
	private ThumbnailDao tDao = new ThumbnailDao();
	private BoardDao bDao = new BoardDao();

	public ArrayList<Thumbnail> selectList() {
		Connection con = getConnection();
		
		ArrayList<Thumbnail> list = tDao.selectList(con);
		
		
		return list;
	}

	public int insertThumbnail(Thumbnail t, ArrayList<Attachment> list) {
		Connection con = getConnection();
		
		int result = 0;
		
		// 1. 썸네일 게시글 추가 쿼리 실행
		int result1 = tDao.insertThumbnail(con, t);
		
		if( result1 > 0 ) {
			int bno = tDao.selectCurrentBno(con);
			
			for(int i = 0 ; i < list.size() ; i++ ) {
				
				list.get(i).setBno(bno);
			}
		}
		
		
		// 2. 첨부파일 여러 개 추가 쿼리 실행
		int result2 = tDao.insertAttachment(con, list);
		
		if(result1 > 0 && result2 > 0) { 
			commit(con);
			result = 1;
		}
		else rollback(con);
		
		close(con);
		
		
		return result;
	}

	public HashMap<String, Object> selectThumbnilMap(int bno) {
		// Dao 로부터 꺼내오고자 하는 특정 객체들을 조회하여
		// Map 에 하나씩 담는 절차
		Connection con = getConnection();
		
		HashMap<String, Object> hmap = null;
		
		hmap = tDao.selectThumbnailMap(con, bno);
		
		if(hmap != null) {
			
			int result;
			
			try {
				
				result = bDao.updateReadCount(con, bno);
				
				if( result > 0) commit(con);
				else rollback(con);
				
			} catch (BoardException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		close(con);
		
		
		
		return hmap;
	}

	public HashMap<String, Object> getUpdateView(int bno) {
		Connection con = getConnection();
		
		HashMap<String, Object> hmap = tDao.selectThumbnailMap(con, bno);
		
		close(con);
		
		
		return hmap;
	}

	public int updateThumbnail(Thumbnail t, ArrayList<Attachment> list) {
		Connection con = getConnection();
		
		int result = 0;
		
		int result1 = tDao.updateThumbnail(con, t);
		
		if(result1 > 0) {
			int result2 = tDao.updateAttachment(con, list);
			
			if(result2 > 0) {
				commit(con);
				result = 1;
			}else rollback(con);
		}
		
		close(con);
		
		return result;
	}

}
