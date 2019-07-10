package com.kh.jsp.thumbnail.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.thumbnail.model.service.ThumbnailService;

/**
 * Servlet implementation class UpdateViewThumbnailServlet
 */
@WebServlet("/tUpView.tn")
public class UpdateViewThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateViewThumbnailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		HashMap<String, Object> hmap = new ThumbnailService().getUpdateView(bno);
		
		String page = "";
		
		if(hmap != null && hmap.get("attachment") != null) {
			
			page = "views/thumbnail/thumbnailUpdateView.jsp";
			
			request.setAttribute("thumbnail", hmap.get("thumbnail"));
			request.setAttribute("fileList", hmap.get("attachment"));
			
		} else {
			
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "사진 게시글 수정 페이지 조회 실패!");
			
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
