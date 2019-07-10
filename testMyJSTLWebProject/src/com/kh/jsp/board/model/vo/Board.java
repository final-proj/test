package com.kh.jsp.board.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class Board implements Serializable{
	
	private static final long serialVersionUid = 100L;
	
	// 필드 변수 : DB - Column
	
	// 만약 DB의 테이블 정보와 다르게 VO에 더 많은 값을 담거나
	// DB의 JOIN 정보를 활용한 데이터들을 사용하고자 한다면
	// VO의 정보를 테이블과 1:1로 연결시키지 않아도 된다.
	
	// VO ~ DB Table 양측의 정보가 일치하지 않아도 된다.
	private int bno; // 게시글 번호
	private int btype; // 게시판 종류
	private String btitle; // 제목
	private String bcontent; // 게시글 내용
	private String bwriter; // 게시글 작성자
	private String bwriterId; // DB로 부터 가져올 게시글 작성자 아이디
	private int bcount; // 게시글 조회수
	private String boardfile; // 게시글 첨부파일
	private Date bdate; // 작성일
	private String status; // 삭제여부('Y' 이면 삭제 X , 'N' 이면 삭제 O )
	
	// 생성자 : 클래스 new 로 사용하고 싶은 형식
	public Board() {
		super();
	}

	public Board(String btitle, String bcontent, String bwriter, String boardfile) {
		super();
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bwriter = bwriter;
		this.boardfile = boardfile;
	}

	public Board(int bno, int btype, String btitle, String bcontent, String bwriter, int bcount, String boardfile,
			Date bdate, String status) {
		super();
		this.bno = bno;
		this.btype = btype;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bwriter = bwriter;
		this.bcount = bcount;
		this.boardfile = boardfile;
		this.bdate = bdate;
		this.status = status;
	}

	// 객체 이름으로 모든 내용 조회하기
	@Override
	public String toString() {
		return "Board [bno=" + bno + ", btype=" + btype + ", btitle=" + btitle + ", bcontent=" + bcontent + ", bwriter="
				+ bwriter + ", bcount=" + bcount + ", boardfile=" + boardfile + ", bdate=" + bdate + ", status="
				+ status + "]";
	}

	// 필드 변수에 대한 Getter / Setter
	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public int getBtype() {
		return btype;
	}

	public void setBtype(int btype) {
		this.btype = btype;
	}

	public String getBtitle() {
		return btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public String getBwriter() {
		return bwriter;
	}

	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}

	public int getBcount() {
		return bcount;
	}

	public void setBcount(int bcount) {
		this.bcount = bcount;
	}

	public String getBoardfile() {
		return boardfile;
	}

	public void setBoardfile(String boardfile) {
		this.boardfile = boardfile;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBwriterId() {
		return bwriterId;
	}

	public void setBwriterId(String bwriterId) {
		this.bwriterId = bwriterId;
	}
	
	
	
	
	
	
	
	

}
