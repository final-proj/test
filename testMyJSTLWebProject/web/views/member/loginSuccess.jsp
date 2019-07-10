<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.kh.jsp.member.model.vo.*" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공 화면!</title>
</head>
<body>
	<c:import url="/views/common/header.jsp" />
	
	<h1>로그인 성공!</h1>
	<h3>${ member.userName }님, 환영합니다!!</h3>
	<p>회원 정보 : <br>
	   ${ member }
	</p>
	
	<br /><br />
	
	<a href="/myWeb/index.jsp">처음으로 돌아가기</a>
	
	<button type="button" id="logoutBtn" onclick="logout()">로그아웃</button>
		
	<script>
		function logout(){
			location.href = '/myWeb/logout.me';
		}
	</script>
	<c:import url="/views/common/footer.jsp" />
</body>
</html>








