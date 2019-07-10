package com.kh.jsp.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.notice.model.exception.NoticeException;
import com.kh.jsp.notice.model.service.NoticeService;
import com.kh.jsp.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateViewServlet
 */
@WebServlet("/nUpView.no")
public class NoticeUpdateViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int nno = Integer.parseInt(request.getParameter("nno"));
		
		
		String page = "";
		
		try {
			
			Notice n = new NoticeService().updateView(nno);
			System.out.println("수정 화면 가져오기 성공!");
			page = "views/notice/noticeUpdate.jsp";
			request.setAttribute("notice", n);
			request.getRequestDispatcher(page).forward(request, response);
			
		}catch(NoticeException e) {
			
			request.setAttribute("msg", "공지사항 수정 페이지 연결 실패!!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
//		if( n != null ) {
//			
//			page = "views/notice/noticeUpdate.jsp";
//			request.setAttribute("notice", n);
//			
//		}else {
//			
//			page = "views/common/errorPage.jsp";
//			request.setAttribute("msg", "공지사항 수정 페이지 연결 실패!!");
//			
//		}
//		
//		request.getRequestDispatcher(page).forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
