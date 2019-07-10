<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.kh.jsp.notice.model.vo.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 목록표</title>
<script src="${ pageContext.request.contextPath }/resources/js/jquery-3.4.1.min.js"></script>

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
		text-align:center;
	}
	.tableArea {
		width:650px;
		height:350px;
		margin-left:auto;
		margin-right:auto;
	}
	.searchArea {
		width:650px;
		margin-left:auto;
		margin-right:auto;
	}
</style>
</head>
<body>
	<c:import url="../common/header.jsp" />
	<div class="outer">
		<br>
		<h2 align="center">공지사항</h2>
		<div class="tableArea">
			<table align="center" id="listArea">
			<tr>
				<th>글번호</th>
				<th width="300px">글제목</th>
				<th width="100px">작성자</th>
				<th>조회수</th>
				<th width="100px">작성일</th>
			</tr>
			<c:forEach var="n" items="${ list }">
			
			<tr>
				<td>${n.nno}</td>
				<td>${n.ntitle}</td>
				<td>${n.nwriter}</td>
				<td>${n.ncount}</td>
				<td>${n.ndate}</td>
			</tr>
			</c:forEach>
			
		</table>
		</div>
		<div class="searchArea" align="center">
			<select id="searchCondition" name="searchCondition">
				<option value="">---</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="search" id="keyword" placeholder="키워드를 입력하세요!"> 
			<button type="button" onclick="search();">검색하기</button>
			<c:if test="${ !empty member and member.userId eq 'admin' }" >
			
				<button onclick="location.href='views/notice/noticeInsertForm.jsp'">작성하기</button>
			
			</c:if>
		</div>
	</div>
	<script>  
		$(function(){
			
			$("#listArea td").mouseenter(function(){
				$(this).parent().css({"background":"darkgray", "cursor":"pointer"});
			}).mouseout(function(){
				$(this).parent().css({"background":"black"});
			}).click(function(){
				//console.log($(this).parent().children().eq(0).text());
				var nno = $(this).parent().children().eq(0).text();
				location.href="${ pageContext.request.contextPath }/selectOne.no?nno=" + nno;
			});
		});
		
		function search(){
			location.href="${ pageContext.request.contextPath }/searchNotice.no?con="+$('#searchCondition').val()+"&keyword="+$('#keyword').val();
		}
		
	</script>
	<c:import url="../common/footer.jsp" />
</body>
</html>