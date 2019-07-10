package com.kh.jsp.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.jsp.member.model.exception.MemberException;
import com.kh.jsp.member.model.service.MemberService;
import com.kh.jsp.member.model.vo.Member;



/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/mUpdate.me")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		// 회원 정보 수정용 데이터 꺼내오기
		
		String pwd = request.getParameter("userPwd");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("tel1")+"-"+request.getParameter("tel2")+"-"+request.getParameter("tel3");
		String address = request.getParameter("zipCode")+"/ "+request.getParameter("address1")+"/ "+request.getParameter("address2");
		String hobby = String.join(", ", request.getParameterValues("hobby"));
		
		// 해당 회원을 구분짓는 ID 받아오기
		HttpSession session = request.getSession(false);
		
		Member m = (Member)session.getAttribute("member");
		
		// 기존의 회원 정보를 새로운 값으로 변경하기
		m.setUserPwd(pwd);
		m.setAge(age);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address);
		m.setHobby(hobby);
		
		System.out.println("변경한 회원 정보 확인 : " + m);
		
		MemberService ms = new MemberService();
		try {
			
			ms.updateMember(m);
			System.out.println("회원 정보 수정 완료!!");
			
			response.sendRedirect("index.jsp");
			
		}catch(MemberException e) {
			
			request.setAttribute("msg", "회원 정보 수정 중 에러 발생!!");
			request.setAttribute("expception", e);
			
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
