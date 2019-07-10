<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 작성</title>
<style>
	.outer{
		width:800px;
		height:500px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	
	table {
		border:1px solid white;
	}

	.tableArea {
		width:450px;
		height:350px;
		margin-left:auto;
		margin-right:auto;
	}
</style>
</head>
<body>
	<c:import url="../common/header.jsp" />
	<c:if test="${ !empty member and member.userId eq 'admin' }" >
	<div class="outer">
		<br>
		<h2 align="center">공지 사항 작성</h2>
		<div class="tableArea">
			<form action="${ pageContext.request.contextPath }/nInsert.no" method="post">
				<table>
					<tr>
						<td>제목 </td>
						<td colspan="3"><input type="text" size="50" name="title"></td>
					</tr>
					<tr>
						<td>작성자 </td>
						<td>
							<input type="text" value="${member.userName}" name="writer" readonly>
							<input type="hidden" value="${member.userId}" name="userId">
						</td>
						<td>작성일</td>
						<td><input type="date" name="date"></td>
					</tr>
					<tr>
						<td>내용 </td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="content" id="area1" cols="60" rows="15" style="resize:none;"></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="reset" onclick="location.href='${ pageContext.request.contextPath }/selectList.no'">취소하기</button>
					<button type="submit">등록하기</button>
				</div>
				
			</form>
			
		</div>
	</div>
	</c:if>
	<c:if test="${ empty member or member.userId ne 'admin' }" >
	
		<c:url var="errorPage" value="../common/errorPage.jsp">
			<c:param name="msg" value="관계자 외에 접근이 불가능한 페이지 입니다." />
		</c:url>
		<c:redirect url="${ errorPage }" />
	
	</c:if>
	<c:import url="../common/footer.jsp" />
	<!--  <script>
		var str = $("#area1").val();
	
		str = str.replace(/(?:\r\n|\r|\n)/g, "<br/>");

		$("#area1").val(str);
	</script>
	-->
</body>
</html>