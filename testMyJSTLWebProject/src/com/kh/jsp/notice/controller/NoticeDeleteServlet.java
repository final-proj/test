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
 * Servlet implementation class NoticeDeleteServlet
 */
@WebServlet("/nDelete.no")
public class NoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int nno = Integer.parseInt(request.getParameter("nno"));

		NoticeService ns = new NoticeService();

		try {

			ns.deleteNotice(nno);
			System.out.println("공지 사항 삭제 성공!");

			response.sendRedirect("selectList.no");

		} catch (NoticeException e) {

			request.setAttribute("msg", "공지사항 삭제 실패!!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);

		}

//		if( result > 0 ) {
//			
//			response.sendRedirect("selectList.no");
//			
//		}else {
//			
//			request.setAttribute("msg", "공지사항 삭제 실패!!");
//			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
//			
//		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
