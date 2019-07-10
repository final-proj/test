package com.kh.jsp.thumbnail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.boardComment.model.service.BoardCommentService;
import com.kh.jsp.boardComment.model.vo.BoardComment;
import com.kh.jsp.thumbnail.model.service.ThumbnailService;

/**
 * Servlet implementation class SelectOneThumbnailServlet
 */
@WebServlet("/selectOne.tn")
public class SelectOneThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectOneThumbnailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		// 만약에 DB로부터 전달 받고자하는 객체가 여러 개 일 경우 두 가지 방법으로 처리할 수 있다.
		// 1. 새로운 통합형 VO를 만들기
		//		- 장점 : 한 번 만들어 두면 이용하기 편리하다.
		// 		- 단점 : 객체 자체의 메모리 사용률이 올라 서비스 자체가 느려질 수 있다.
		//
		// 2. Map 객체를 활용하여 여러 클래스(객체)를 key와 value로 묶어서 관리하는 방법
		// 		- 장점 : 여러 객체의 정보가 하나의 map에 들어가기 때문에 관리하기 편리하다.
		// 				ex) "thumb" -> Thumbnail / "att" -> Attachment
		// 		- 단점 : 각 객체의 내용이 변경 될 경우 이를 직접 확인하기 어렵다.
		HashMap<String, Object> thumb = new ThumbnailService().selectThumbnilMap(bno);
		
		// System.out.println(thumb);
		ArrayList<BoardComment> clist = new BoardCommentService().selectList(bno);
		
		String page = "";
		
		if(thumb != null && thumb.get("attachment") != null) {
			
			page = "views/thumbnail/thumbnailDetail.jsp";
			
			request.setAttribute("thumbnail", thumb.get("thumbnail"));
			request.setAttribute("fileList", thumb.get("attachment"));
			request.setAttribute("clist", clist);
			
		} else {
			
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "사진 게시글 상세보기 실패!!");
			
		}
		
		request.getRequestDispatcher(page).forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
