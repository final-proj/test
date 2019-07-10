package com.kh.jsp.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.board.model.exception.BoardException;
import com.kh.jsp.board.model.service.BoardService;
import com.kh.jsp.board.model.vo.Board;
import com.kh.jsp.boardComment.model.service.BoardCommentService;
import com.kh.jsp.boardComment.model.vo.BoardComment;

/**
 * Servlet implementation class BoardSelectOneServlet
 */
@WebServlet("/selectOne.bo")
public class BoardSelectOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSelectOneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		Board b = null;
		ArrayList<BoardComment> clist = null;
		String page;
		
		try {
			
			b = new BoardService().selectOne(bno);
			clist = new BoardCommentService().selectList(bno);
			
			
			page = "views/board/boardDetail.jsp";
			request.setAttribute("board", b);
			request.setAttribute("clist", clist);
			
			request.getRequestDispatcher(page).forward(request, response);
			
		} catch (BoardException e) {
			
			page = "views/common/errorPage.jsp";
			request.setAttribute("msg", "게시글 상세보기 실패!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher(page).forward(request, response);
			
		}
		
//		if( b != null) {
//			
//			page = "views/board/boardDetail.jsp";
//			request.setAttribute("board", b);
//			request.setAttribute("clist", clist);
//			
//		}else {
//			
//			page = "views/common/errorPage.jsp";
//			request.setAttribute("msg", "게시글 상세보기 실패!");
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
