<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*, com.kh.jsp.thumbnail.model.vo.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진 게시판</title>
<style> 
	.outer{
		width : 1000px;
		height : auto;
		background : black;
		color : white;
		margin-left : auto;
		margin-right : auto;
		margin-top : 50px;
	}
	
	.thumbnailArea {
      width:760px;
      height:auto;
      margin-left:auto;
      margin-right:auto;
   }
   .searchArea {
      width:420px;
      margin-left:auto;
      margin-right:auto;
   }
   .thumb-list {
      width:220px;
      border:1px solid white;
      display:inline-block;
      margin:10px;
      align:center;
   }
   .thumb-list:hover {
      opacity:0.8;
      cursor:pointer;
   }


</style>
</head>
<body>
	<c:import url="/views/common/header.jsp" />

	<div class="outer">
		<br>
		<h2 align="center">사진게시판</h2>

		<div class="thumbnailArea">
		
			<c:forEach var="thumb" items="${ list }">
			
			<div class="thumb-list" align="center">
				<div>
					<input type="hidden" value="${ thumb.bno }"> <img
						src="/myWeb/resources/thumbnailUploadFiles/${ thumb.boardfile }"
						width="200px" height="150px">
				</div>
				<p>
					No.
					${ thumb.bno }&nbsp;&nbsp;${ thumb.btitle }<br> 조회수 :
					${ thumb.bcount }
				</p>
			</div>
			
			</c:forEach>
			
		</div>
		<script>
         $(function(){
            $(".thumb-list").click(function(){
               var bno = $(this).children().children().eq(0).val();
               //console.log(num);
               location.href="${ pageContext.request.contextPath }/selectOne.tn?bno=" + bno;
								});
			});
		</script>

		<div class="searchArea">
			<select id="searchCondition" name="searchCondition">
				<option>---</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select> <input type="search">
			<button type="submit">검색하기</button>
			
			<c:if test="${ !empty member }">
			
			<button
				onclick="location.href='views/thumbnail/thumbnailInsertForm.jsp'">작성하기</button>
			
			</c:if>
			
		</div>
		<br>
		<br>
	</div>

	<c:import url="/views/common/footer.jsp" />
</body>
</html>