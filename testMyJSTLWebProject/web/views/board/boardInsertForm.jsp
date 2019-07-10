<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<style>
	.outer {
		width:900px;
		height:500px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	table {
		padding: 15px;
		border:1px solid white;
	}

	.tableArea {
		width:500px;
		height:350px;
		margin-left:auto;
		margin-right:auto;
	}
</style>

</head>
<body>
	<c:import url="../common/header.jsp" />
	
	<c:if test="${ !empty member }">
	
	<div class="outer">
		<br>
		<h2 align="center">게시판 작성</h2>
		<div class="tableArea">
			<form action="${ pageContext.request.contextPath }/bInsert.bo" 
			      method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td>제목 </td>
						<td colspan="3"><input type="text" size="51" name="title"></td>
					</tr>
					<tr>
						<td>작성자 </td>
						<td colspan="3">${ member.userName }
							<input type="hidden" name="userId" value="${ member.userId }"/>
						</td>
					</tr>
					<tr>
						<td>첨부파일 </td>
						<td colspan="3">
							<input type="file" name="file" id="file" />
						</td>
					</tr>
					<tr>
						<td>내용 </td>
						<td colspan="3">
							<textarea name="content" cols="52" rows="15" style="resize:none;"></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="reset" onclick="location.href='${ pageContext.request.contextPath }/selectList.bo'">취소하기</button>
					<button type="submit">등록하기</button>
				</div>
			</form>
		</div>
	</div>
	</c:if>
	<c:if test="${ empty member }">
	
		<c:url var="errorPage" value="views/common/errorPage.jsp">
			<c:param name="msg" value="회원만 가능한 서비스 입니다." />
		</c:url>
		<c:redirect url="${ errorPage }" />
	
	 
	 </c:if>
	<c:import url="../common/footer.jsp" />
</body>
</html>