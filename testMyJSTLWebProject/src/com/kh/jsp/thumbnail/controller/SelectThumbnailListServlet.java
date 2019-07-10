package com.kh.jsp.thumbnail.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.thumbnail.model.vo.Thumbnail;
import com.kh.jsp.thumbnail.model.service.ThumbnailService;

/**
 * Servlet implementation class SelectThumbnailListServlet
 */
@WebServlet("/selectList.tn")
public class SelectThumbnailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectThumbnailListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Thumbnail> list = null;
		
		list = new ThumbnailService().selectList();
		
		// ver.2 : list 확인용 소스 코드
		// System.out.println("list : " + list);
		// System.out.println("list size : " + list.size());
		
		String page = "";
		
		if(list != null) {
			
			page = "views/thumbnail/thumbnailList.jsp";
			request.setAttribute("list", list);
			
		}else {
			
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "사진 게시판 목록 조회 실패");
			
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
