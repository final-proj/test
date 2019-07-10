<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.jsp.board.model.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	
	td {
		border:1px solid white;
	}

	.tableArea {
		border:1px solid white;
		width:800px;
		height:360px;
		margin-left:auto;
		margin-right:auto;
	}
	.tableArea table {
		width : 600px;
	}
	#content {
		height:230px;
	}
</style>
<title>게시글 수정 페이지</title>
</head>
<body>
<c:import url="../common/header.jsp" />
	<div class="outer">
		<br>
		<h2 align="center">게시글 수정 페이지</h2>
		<div class="tableArea">
			<form id="updateForm" method="post" enctype="multipart/form-data">
				<table align="center">
					<tr>
						<td>제목 </td>
						<td colspan="3">
							<input type="text" size="65" name="title" value="${ board.btitle }">
							<input type="hidden" name="bno" value="${ board.bno }">
						</td>
					</tr>
					<tr>
						<td>작성자 </td>
						<td colspan="3">${ board.bwriter }</td>
					</tr>
					<c:if test="${ !empty board.boardfile }">
					
					<tr>
						<td>기존 파일 </td>
						<td colspan="3">
							<a href="/myWeb/bfdown.bo?path=${ board.boardfile }">
							${ board.boardfile }</a>	
						</td>
					</tr>
					</c:if>
					
					<tr>
						<td>새 파일 </td>
						<td colspan="3">
							<input type="file" name="file" id="file" />
						</td>
					</tr>
					<tr>
						<td>내용 </td>
						<td colspan="3">
							<textarea name="content" cols="67" rows="15" style="resize:none;">${ board.bcontent }</textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="reset" onclick="location.href='${ pageContext.request.contextPath }/selectList.bo'">취소하기</button>
					<button onclick="complete();">수정완료</button>
					<button onclick="deleteBoard();">삭제하기</button>
				</div>
				<script>
					function complete(){
						$("#updateForm").attr("action","${ pageContext.request.contextPath }/bUpdate.bo");
					}
					
					function deleteBoard(){
						$("#updateForm").attr("action","${ pageContext.request.contextPath }/bDelete.bo");
					}
				
				</script>
			</form>
	</div>
	</div>
	<c:import url="../common/footer.jsp" />
</body>
</html>