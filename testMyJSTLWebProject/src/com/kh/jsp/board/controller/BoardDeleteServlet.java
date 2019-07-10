package com.kh.jsp.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.jsp.board.model.exception.BoardException;
import com.kh.jsp.board.model.service.BoardService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/bDelete.bo")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int maxSize = 1024 * 1024 * 10;
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "multipart를 통한 전송이 아닙니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);

		}
		
		String root = request.getServletContext().getRealPath("/");

		// 게시판의 첨부파일을 저장할 폴더 이름 지정하기
		// (폴더를 자동으로 만들어 주지 않기 때문에 미리 폴더를 준비해야 한다.)
		String savePath = root + "resources/boardUploadFiles";

		// 4. 실제 담아온 파일 기타 정보들을 활용하여 MultipartRequest 생성하기
		// request --> MultipartRequest

		MultipartRequest mrequest = new MultipartRequest(request, // 변경하기 위한 원본 객체
				savePath, // 파일 저장 경로
				maxSize, // 저장할 파일의 최대 크기
				"UTF-8", // 저장할 문자셋 설정
				new DefaultFileRenamePolicy()
		// 만약에 동일한 이름의 파일을 저장했을 경우 기존의 파일과 구분하기 위해 새로운 파일명 뒤에 숫자를 붙이는 규칙
		// OOO.txt ---> OOO1.txt
		);

		
		int bno = Integer.parseInt(mrequest.getParameter("bno"));

		
		
		
		System.out.println(bno);

		BoardService bs = new BoardService();

		int result;
		
		try {
			
			result = bs.deleteBoard(bno);
			
			System.out.println("게시글 삭제 성공!");
			response.sendRedirect("selectList.bo");
			
			
		} catch (BoardException e) {
			
			request.setAttribute("msg", "게시글 삭제 실패!!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}

//		if (result > 0) {
//
//			System.out.println("게시글 삭제 성공!");
//			response.sendRedirect("selectList.bo");
//
//		} else {
//
//			request.setAttribute("msg", "게시글 삭제 실패!!");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
