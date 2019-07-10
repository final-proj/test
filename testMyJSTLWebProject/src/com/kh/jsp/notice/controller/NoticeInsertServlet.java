package com.kh.jsp.notice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.notice.model.exception.NoticeException;
import com.kh.jsp.notice.model.service.NoticeService;
import com.kh.jsp.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertServlet
 */
@WebServlet("/nInsert.no")
public class NoticeInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 공지사항 제목, 작성자, 내용
		String ntitle = request.getParameter("title");
		String writer = request.getParameter("userId");
		String content = request.getParameter("content").replace("\r\n","<br>");
		
		// request.getParameter() 는 사용자가 요청하면서 전달한 값들을
		// 문자열 형태로 받아오는 메소드이다.
		String date = request.getParameter("date");
		
		System.out.println("날짜 전달 확인 : " + date);
		
		Date writeDate = null;
		
		if(date != "" && date != null) {
			// 날짜가 들어 왔다면
			// 2019-05-24  -> 2019, 5, 24
			String[] dateArr = date.split("-");
			int[] intArr = new int[dateArr.length];
			
			// String --> int
			
			for(int i = 0 ; i < dateArr.length ; i++ ) {
				intArr[i] = Integer.parseInt(dateArr[i]);
			}
			
			// writeDate = new Date(intArr[0], intArr[1]-1, intArr[2]);
			writeDate = new Date(
					new GregorianCalendar(intArr[0], intArr[1]-1, intArr[2]).getTimeInMillis());
		
		} else {
			// 날짜가 들어오지 않았다면
			writeDate = new Date(new GregorianCalendar().getTimeInMillis());
		}
		
		// 공지사항 등록을 위한 vo 객체 만들기
		Notice n = new Notice();
		
		n.setNtitle(ntitle);
		n.setNwriter(writer);
		n.setNcontent(content);
		n.setNdate(writeDate);
		
		NoticeService ns = new NoticeService();
		
		//int result = ns.insertNotice(n);
		
		// View(JSP) --->  Controller(Servlet) ---> Service
		
		try {
			
			ns.insertNotice(n);
			System.out.println("공지사항 추가 완료!");
			
			response.sendRedirect("selectList.no");
			
		}catch(NoticeException e) {
			
			request.setAttribute("msg", "공지사항 등록 실패!!");
			request.setAttribute("exception", e);
			
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
//		if( result > 0 ) {
//			// 게시글 추가 성공
//			
//			response.sendRedirect("selectList.no");
//			
//		}else {
//			// 공지사항 추가 실패
//			
//			request.setAttribute("msg", "공지사항 등록 실패!!");
//			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
//			
//			
//		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
