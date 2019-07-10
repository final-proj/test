<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.jsp.notice.model.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% Notice n = (Notice)request.getAttribute("notice"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<title>공지 사항 수정</title>
</head>
<body>
	<c:import url="../common/header.jsp" />
	<c:if test="${ !empty member and member.userId eq 'admin' }" >
	
	<div class="outer">
		<br>
		<h2 align="center">공지 사항 수정</h2>
		<div class="tableArea">
			<form id="updateForm" method="post">
				<table>
					<tr>
						<td>제목 </td>
						<td colspan="3">
							<input type="text" size="50" name="title" 
							       value="${ fn:replace(notice.ntitle, '\"', '&#34;') }">
							<input type="hidden" name="nno" value="${ notice.nno }">
						</td>
					</tr>
					<tr>
						<td>작성자 </td>
						<td>
							<input type="text" value="${ notice.nwriter }" name="writer" readonly>
						</td>
						<td>작성일</td>
						<td><input type="date" name="date" value="${ notice.ndate }"></td>
					</tr>
					<tr>
						<td>내용 </td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="content" cols="60" rows="15" style="resize:none;">${ notice.ncontent }</textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="reset" onclick="location.href='${ pageContext.request.contextPath }/selectList.no'">취소하기</button>
					<button onclick="complete();">작성완료</button>
					<button onclick="deleteNotice();">삭제하기</button>
				</div>
				<script>
					function complete(){
						$("#updateForm").attr("action","${ pageContext.request.contextPath }/nUpdate.no");
						
					}
					
					function deleteNotice(){
						// delete 는 예약어 이므로 deleteNotice 로 ...!
						$("#updateForm").attr("action","${ pageContext.request.contextPath }/nDelete.no");
					}
				
				</script>
			</form>
			
		</div>
	</div>
	</c:if>
	<c:if test="${ empty member and !(member.userId eq 'admin') }" >
	
		<c:url var="errorPage" value="../common/errorPage.jsp">
			<c:param name="msg" value="관계자 외에 접근이 불가능한 페이지 입니다." />
		</c:url>
		<c:redirect url="${ errorPage }" />
		
	 
	</c:if>
	<c:import url="../common/footer.jsp" />
</body>
</html>