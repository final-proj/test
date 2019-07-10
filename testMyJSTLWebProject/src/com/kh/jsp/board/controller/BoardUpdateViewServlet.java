package com.kh.jsp.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.board.model.exception.BoardException;
import com.kh.jsp.board.model.service.BoardService;
import com.kh.jsp.board.model.vo.Board;

/**
 * Servlet implementation class BoardUpdateViewServlet
 */
@WebServlet("/bUpView.bo")
public class BoardUpdateViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		String page = "";
		
		Board b;
		
		try {
			
			b = new BoardService().updateView(bno);
			
			System.out.println("수정 화면 가져오기 성공!");
			page = "views/board/boardUpdate.jsp";
			request.setAttribute("board", b);
			
			request.getRequestDispatcher(page).forward(request, response);
			
		} catch (BoardException e) {
			
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "게시판 수정 페이지 연결 실패!!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher(page).forward(request, response);
			
		}
		
//		if( b != null ) {
//			
//			System.out.println("수정 화면 가져오기 성공!");
//			page = "views/board/boardUpdate.jsp";
//			request.setAttribute("board", b);
//			
//			
//		}else {
//			
//			page = "views/common/errorPage.jsp";
//			request.setAttribute("msg", "게시판 수정 페이지 연결 실패!!");
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
