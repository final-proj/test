package com.kh.jsp.thumbnail.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

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

/**
 * Servlet implementation class UpdateThumbnailServlet
 */
@WebServlet("/tUpdate.tn")
public class UpdateThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateThumbnailServlet() {
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
		
		int maxSize = 1024*1024*10;
		
		String root = request.getServletContext().getRealPath("/resources");
		
		String savePath = root + "/thumbnailUploadFiles/";
		
		MultipartRequest mrequest = new MultipartRequest(
				request, savePath, maxSize, "UTF-8", new MyRenamePolicy());
		
		int bno = Integer.parseInt(mrequest.getParameter("bno"));
		// 원본 파일의 경로 확인하기
		HashMap<String, Object> hmap = ts.selectThumbnilMap(bno);
		
		ArrayList<String> saveFiles = new ArrayList<String>();
		ArrayList<String> originFiles = new ArrayList<String>();
		
		// 전달된 파일 정보 가져오기
		Enumeration<String> files = mrequest.getFileNames();
		
		while(files.hasMoreElements()) {
			
			String name = files.nextElement();
			
			saveFiles.add(mrequest.getFilesystemName(name));
			originFiles.add(mrequest.getOriginalFileName(name));
			
		}
		
		// -- 수정이 완료되었을 때 이전에 저장되었던 파일은 삭제시켜줘야 함.
		
		Thumbnail t = (Thumbnail)hmap.get("thumbnail");
		
		t.setBtitle(mrequest.getParameter("title"));
		t.setBcontent(mrequest.getParameter("content"));
		t.setBwriter(mrequest.getParameter("userId"));
		
		ArrayList<Attachment> list = (ArrayList<Attachment>)hmap.get("attachment");
		
		for(int i = originFiles.size() -1 ; i >= 0 ; i --) {
			
			int j = originFiles.size() - i -1;
			System.out.println(i + " : " + originFiles.get(i));
			System.out.println(j + " :: " + originFiles.get(j));
			if(originFiles.get(i) != null) {
				if(list.get(j) != null)
					new File(savePath + list.get(j).getChangename()).delete();
				
				list.get(j).setFilepath(savePath);
				list.get(j).setChangename(saveFiles.get(i));
				list.get(j).setOriginname(originFiles.get(i));
			}
			
		}
		
		int result = ts.updateThumbnail(t, list);
		
		if(result > 0) {
			
			response.sendRedirect("selectList.tn");
			
		}else {
			
			request.setAttribute("msg", "파일 전송 실패!!");
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
