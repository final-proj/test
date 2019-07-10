package com.kh.jsp.thumbnail.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.jsp.common.MyRenamePolicy;
import com.kh.jsp.thumbnail.model.service.ThumbnailService;
import com.kh.jsp.thumbnail.model.vo.Attachment;
import com.kh.jsp.thumbnail.model.vo.Thumbnail;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class InsertThumbnailServlet
 */
@WebServlet("/tInsert.tn")
public class InsertThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertThumbnailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ThumbnailService ts = new ThumbnailService();
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			// 만약 multipart/form-data 로 보내지 않았으면 에러 발생!!
			
			request.setAttribute("msg", "멀티파트 폼으로 전송해야 합니다!!");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
		}
		
		
		// 1. 전송할 파일의 크기 설정하기
		int maxSize = 1024 * 1024 * 10; // 10MB
		
		// 2. 저장할 경로 설정하기
		String root = request.getServletContext().getRealPath("/resources");
		
		String savePath = root + "/thumbnailUploadFiles/";
		
		// 사용자가 저장하는 파일을 서비스 목적에 맞게 이름을 변경하여 설정할 경우
		// DefaultFileRenamePolicy 를 상속받아 새롭게 정의할 수 있다.
		
		// 기본값 : test.zip --> test1.zip ---> test2.zip
		
		// 카톡 : kakaoTalk_20190530_000000.zip 
		
		MultipartRequest mrequest 
				= new MultipartRequest(request, savePath, maxSize, "UTF-8",	
										new MyRenamePolicy());
		
		// 다중 파일 업로드 시에 처리하는 방법
		// 다중 파일 업로드 시 컬렉션 객체를 활용하여
		// 파일을 별도 관리한다.
		
		// 변경된 파일 명
		ArrayList<String> saveFiles = new ArrayList<String>();
		
		// 원본 파일 명
		ArrayList<String> originFiles = new ArrayList<String>();
		
		// 폼으로 전송된 파일 이름들 가져오기
		Enumeration<String> files
			= mrequest.getFileNames();
		
		while(files.hasMoreElements()) {
			// 각 파일의 정보를 가져와 DB에 저장한다.
			
			String name = files.nextElement();
			
			System.out.println("name : " + name);
			saveFiles.add(mrequest.getFilesystemName(name));
			originFiles.add(mrequest.getOriginalFileName(name));
			
		}
		
		// Thumbnail VO 객체 생성하여 DB에 전달할 내용 채우기
		Thumbnail t = new Thumbnail();
		
		t.setBtitle(mrequest.getParameter("title"));
		t.setBcontent(mrequest.getParameter("content").replace("\r\n","<br>"));
		t.setBwriter(mrequest.getParameter("userId"));
		
		// Attachment 객체에 파일 정보를 저장하여
		// ArrayList 생성하기
		
		ArrayList<Attachment> list = new ArrayList<Attachment>();
		
		System.out.println(originFiles);
		System.out.println(saveFiles);
		
		// 리스트에 파일 목록 하나씩 저장하기
		for(int i = originFiles.size() - 1; i >= 0 ; i--) {
			
			// 기존에 역순으로 저장된 파일 리스트를 올바른 순서로 재 정렬하기
			Attachment at = new Attachment();
			
			at.setFilepath(savePath);
			at.setOriginname(originFiles.get(i));
			at.setChangename(saveFiles.get(i));
			
			list.add(at);
			
		}
		
		int result = ts.insertThumbnail(t, list);
		
		if( result > 0 ) {
			response.sendRedirect("selectList.tn");
		}else {
			request.setAttribute("msg", "파일 전송 실패!!");
			
			// 이전 파일 정보 삭제하기
			for(int i = 0 ; i < saveFiles.size() ; i++ ) {
				File file = new File(savePath + saveFiles.get(i));
				
				System.out.println("파일 삭제 확인 : " + file.delete());
			}
			
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
