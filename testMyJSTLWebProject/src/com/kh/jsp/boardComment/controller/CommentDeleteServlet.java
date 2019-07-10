package com.kh.jsp.boardComment.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.jsp.boardComment.model.service.BoardCommentService;

/**
 * Servlet implementation class CommentDeleteServlet
 */
@WebServlet("/deleteComment.bo")
public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cno = Integer.parseInt(request.getParameter("cno"));
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		BoardCommentService bco = new BoardCommentService();
		
		int result = bco.deleteComment(cno);
		int result2 = bco.btypeBoard(bno);
		
		if( result > 0) {
			
			System.out.println("댓글 삭제 성공!");
			
			if(result2 > 0)	response.sendRedirect("selectOne.bo?bno="+bno);
			else response.sendRedirect("selectOne.tn?bno="+bno);
			
		}else {
			
			request.setAttribute("msg", "댓글 삭제 실패!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
