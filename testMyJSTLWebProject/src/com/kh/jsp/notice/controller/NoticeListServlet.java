package com.kh.jsp.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.notice.model.exception.NoticeException;
import com.kh.jsp.notice.model.service.NoticeService;
import com.kh.jsp.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/selectList.no")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 글 여러 개를 받아 목록 형태로 (ArrayList 형태로)
		// 데이터를 전달하는 서블릿
		
		ArrayList<Notice> list = new ArrayList<>();
		
		NoticeService ns = new NoticeService();
		
		String page = "";
		
		

		try {
		
			list = ns.selectList();
			
			System.out.println("공지사항 목록 보기 성공!!");
			
			page = "views/notice/noticeList.jsp";
			request.setAttribute("list", list);
			
			request.getRequestDispatcher(page).forward(request, response);
			
		}catch(NoticeException e) {
			
			request.setAttribute("msg", "공지사항 목록 보기 실패!!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		
//		if(list != null) {
//			
//			page = "views/notice/noticeList.jsp";
//			request.setAttribute("list", list);
//			
//		}else {
//			
//			page="views/common/errorPage.jsp";
//			request.setAttribute("msg", "공지사항 목록 불러오기 에러!!");
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
