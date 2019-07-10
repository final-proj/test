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
import com.kh.jsp.board.model.vo.Board;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/bUpdate.bo")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardUpdateServlet() {
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

		// 2. multipart/form-data 형식으로 전송되었는지 확인
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 만약 올바른 multipart/form-data로 전송되지 않았을 경우
			// 에러 페이지로 즉시 이동 시킨다.
			request.setAttribute("msg", "multipart를 통한 전송이 아닙니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);

		}

		// 3. 웹 상의 루트(최상위 경로) 경로를 활용하여 저장할 폴더의 위치 지정하기

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

		String title = mrequest.getParameter("title");
		String content = mrequest.getParameter("content").replace("\r\n", "<br>");
		int bno = Integer.parseInt(mrequest.getParameter("bno"));

		// 5-2. 전송된 파일 처리하기
		// 전달받은 파일을 먼저 저장하고, 그 파일의 이름을 가져오는 메소드를 실행한다.
		String fileName = mrequest.getFilesystemName("file");

		// 6. 전송된 파일 VO에 담아 서비스로 보내기
		Board b = new Board();

		b.setBno(bno);
		b.setBtitle(title);
		b.setBcontent(content);
		b.setBoardfile(fileName);

		BoardService bs = new BoardService();
		
		int result;
		
		try {
			
			result = bs.updateBoard(b);
			
			System.out.println("게시글 수정 성공!");

			response.sendRedirect("selectOne.bo?bno=" + bno);
			
		} catch (BoardException e) {
			
			request.setAttribute("msg", "게시글 수정 실패!!");
			request.setAttribute("exception", e);
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}

//		if (result > 0) {
//
//			System.out.println("게시글 수정 성공!");
//
//			response.sendRedirect("selectOne.bo?bno=" + bno);
//			
//		}else {
//			
//			request.setAttribute("msg", "게시글 수정 실패!!");
//			
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
