<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*, com.kh.jsp.boardComment.model.vo.*, com.kh.jsp.thumbnail.model.vo.* " %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사진 상세보기</title>

<style>
	   .outer {
      width:1000px;
      height:650px;
      background:black;
      color:white;
      margin-left:auto;
      margin-right:auto;
      margin-top:50px;
   }
   .detail td{
      text-align:center;
      width:1000px;
      border:1px solid white;
   }
   #titleImgArea {
      width:500px;
      height:300px;
      margin-left:auto;
      margin-right:auto;
   }
   #contentArea {
      height:30px;
   }
   .detailImgArea {
      width:250px;
      height:210px;
      margin-left:auto;
      margin-right:auto;
   }
   #titleImg {
      width:500px;
      height:300px;
   }
   .detailImg {
      width:250px;
      height:180px;
   }
   
   .replyArea {
		width:800px;
		color:white;
		background:black;
		margin-left:auto;
		margin-right:auto;
		margin-bottom:100px;
		padding-bottom : 5px;
	}
	.replyArea textArea {
		border-radius: 10px;
		resize: none;
	}
	a:link {
    	color: yellow;
	}
	a:active {
		color: aqua;
	}
	table[class*="replyList"] * {
		color: black;
		
	}
	.replyList1 td{	background : lavenderblush; }
	.replyList2 td{	background : honeydew; }
	.replyList3 td{ background : aliceblue; }
</style>

</head>
<body>
	<c:import url="../common/header.jsp" />
	
	
	
<div class="outer">
      <table class="detail" align="center">
         <tr>
            <td width="50px">제목</td>
            <td colspan="5"><label>${ thumbnail.btitle }</label></td>
         </tr>
         <tr>
            <td>작성자</td>
            <td><label>${ thumbnail.bwriter }</label></td>
            <td>조회수</td>
            <td><label>${ thumbnail.bcount }</label></td>
            <td>작성일</td>
            <td><label>${ thumbnail.bdate }</label></td>
         </tr>
         <tr>
            <td>대표사진</td>
            <td colspan="4">
               <div id="titleImgArea" align="center">
                  <img id="titleImg" src="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(0).getChangename() }">
               </div>
            </td>
            <td>
               <a href="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(0).getChangename() }" download="${ fileList.get(0).getChangename() }"><button type="button">다운로드</button></a>
            </td>
         </tr>
         <tr>
            <td>사진메모</td>
            <td colspan="6">
               <p id="contentArea">${ thumbnail.bcontent }</p>
            </td>
         </tr>
      </table>
      <table class="detail">
         <tr>
            <td>
               <div class="detailImgArea">
               <c:if test="${ fileList.size() > 1 }">
               
                  <img id="detailImg1" class="detailImg" src="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(1).getChangename() }">
                  <a href="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(1).getChangename() }" download="${ fileList.get(1).getChangename() }"><button type="button">다운로드</button></a>
                  </c:if>
                  <c:if test="${ fileList.size() < 2 }">
                  
                  <p>이미지가 등록되지 않았습니다.</p>
                  </c:if>
                
               </div>
            </td>
            <td>
               <div class="detailImgArea">
               <c:if test="${ fileList.size() > 2 }">
               
                  <img id="detailImg2" class="detailImg" src="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(2).getChangename() }">
                  <a href="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(2).getChangename() }" download="${ fileList.get(2).getChangename() }">
                     <button type="button">다운로드</button>
                  </c:if>
                  <c:if test="${ fileList.size() < 3 }">
                  
                  <p>이미지가 등록되지 않았습니다.</p>
                  </c:if>
                  </a>
               </div>
            </td>
            <td>
               <div class="detailImgArea">
               <c:if test="${ fileList.size() > 3 }">
               
                  <img id="detailImg3" class="detailImg" src="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(3).getChangename() }">
                  <a href="${ pageContext.request.contextPath }/resources/thumbnailUploadFiles/${ fileList.get(3).getChangename() }" download="${ fileList.get(3).getChangename() }"><button type="button">다운로드</button></a>
                  </c:if>
                  
                  <c:if test="${ fileList.size() < 4 }">
                  <p>이미지가 등록되지 않았습니다.</p>
                  
                  </c:if>
               </div>
            </td>
         </tr>
         <tr>
            <td colspan="3">
               <button onclick="location.href='${ pageContext.request.contextPath }/selectList.tn'">메뉴로 돌아가기</button>
               <c:if test="${ !empty member and member.userId eq thumbnail.bwriterId }" >
               
               <button onclick="location.href='${ pageContext.request.contextPath }/tUpView.tn?bno='+${ thumbnail.bno }">수정하기</button>
               <button onclick="location.href='${ pageContext.request.contextPath }/tDelete.tn?bno='+${ thumbnail.bno }">삭제하기</button>
               </c:if>
            </td>
         </tr>
      </table>
      <div class="replyArea">
         <div class="replyWriteArea">
            <form action="/myWeb/insertComment.bo" method="post">
               <input type="hidden" name="writer" value="${ member.userId }"/>
               <input type="hidden" name="bno" value="${ thumbnail.bno }" />
               <input type="hidden" name="refcno" value="0" />
               <input type="hidden" name="clevel" value="1" />
               
               <table align="center">
                  <tr>
                     <td>댓글 작성</td>
                     <td><textArea rows="3" cols="80" id="replyContent" name="replyContent"></textArea></td>
                     <td><button type="submit" id="addReply">댓글 등록</button></td>
                  </tr>
               </table>
            </form>
         </div>
         <div id="replySelectArea">
         <!-- 게시글의 댓글들을 보여주는 부분  -->
         
         <c:if test="${ !empty clist }" >
         	
         	<c:forEach var="bc" items="${ clist }">
         	
         	
         	<table id="replySelectTable"
             style="margin-left : ${ (bc.clevel-1) * 15 }px;
                   width : ${800 - ((bc.clevel - 1) * 15) }px;"
             class="replyList${ bc.clevel }">
              <tr>
                 <td rowspan="2"> </td>
               <td><b>${ bc.cwriter }</b></td>
               <td>${ bc.cdate }</td>
               <td align="center">
               <c:if test="${ member.userId eq bc.cwriterId }">
               
                  <input type="hidden" name="cno" value="${ bc.cno }"/>
                       
                  <button type="button" class="updateBtn" 
                     onclick="updateReply(this);">수정하기</button>
                     
                  <button type="button" class="updateConfirm"
                     onclick="updateConfirm(this);"
                     style="display:none;" >수정완료</button> &nbsp;&nbsp;
                     
                  <button type="button" class="deleteBtn"
                     onclick="deleteReply(this);">삭제하기</button>
                     
               </c:if>
               <c:if test="${ bc.clevel < 3 }">      
               
                  <input type="hidden" name="writer" value="${ member.userId }"/>
                  <input type="hidden" name="refcno" value="${ bc.cno }" />
                  <input type="hidden" name="clevel" value="${ bc.clevel }" />
                  <button type="button" class="insertBtn" 
                      onclick="reComment(this);">댓글 달기</button>&nbsp;&nbsp;
                      
                  <button type="button" class="insertConfirm"
                     onclick="reConfirm(this);"
                     style="display:none;" >댓글 추가 완료</button> 
                     
               </c:if>
               <c:if test="${ member.userId ne bc.cwriterId and bc.clevel > 3 }">
                  <span> 마지막 레벨입니다.</span>
               </c:if>
               </td>
            </tr>
            <tr class="comment replyList${ bc.clevel }">
               <td colspan="3" style="background : transparent;">
               <textarea class="reply-content" cols="105" rows="3"
                readonly="readonly">${ bc.ccontent }</textarea>
               </td>
            </tr>
         </table>
         	
         </c:forEach>	
        
         </c:if>
         <c:if test="${ empty clist }" >
         
         	<p>현재 등록된 댓글이 없습니다. <br />
         		첫 댓글의 주인공이 되어 보세요!</p>
         
         </c:if>
         
         </div>
      </div>
   </div>
   <script>
   function updateReply(obj) {
      // 현재 위치와 가장 근접한 textarea 접근하기
      $(obj).parent().parent().next().find('textarea')
      .removeAttr('readonly');
      
      // 수정 완료 버튼을 화면 보이게 하기
      $(obj).siblings('.updateConfirm').css('display','inline');
      
      // 수정하기 버튼 숨기기
      $(obj).css('display', 'none');
   }
   
   function updateConfirm(obj) {
      // 댓글의 내용 가져오기
      var content
        = $(obj).parent().parent().next().find('textarea').val();
      
      // 댓글의 번호 가져오기
      var cno = $(obj).siblings('input').val();
      
      // 게시글 번호 가져오기
      var bno = '${ thumbnail.bno }';
      
      location.href="/myWeb/updateComment.bo?"
             +"cno="+cno+"&bno="+bno+"&content="+content;
   }
   
   function deleteReply(obj) {
      // 댓글의 번호 가져오기
      var cno = $(obj).siblings('input').val();
      
      // 게시글 번호 가져오기
      var bno = '${ thumbnail.bno }';
      
      location.href="/myWeb/deleteComment.bo"
      +"?cno="+cno+"&bno="+bno;
   }
   
   function reComment(obj){
      // 추가 완료 버튼을 화면 보이게 하기
      $(obj).siblings('.insertConfirm').css('display','inline');
      
      // 클릭한 버튼 숨기기
      $(obj).css('display', 'none');
      
      // 내용 입력 공간 만들기
      var htmlForm = 
         '<tr class="comment"><td></td>'
            +'<td colspan="3" style="background : transparent;">'
               + '<textarea class="reply-content" style="background : ivory;" cols="105" rows="3"></textarea>'
            + '</td>'
         + '</tr>';
      
      $(obj).parents('table').append(htmlForm);
      
   }
   
   function reConfirm(obj) {
      // 댓글의 내용 가져오기
      
      // 참조할 댓글의 번호 가져오기
      var refcno = $(obj).siblings('input[name="refcno"]').val();
      var level = Number($(obj).siblings('input[name="clevel"]').val()) + 1;
      
      // console.log(refcno + " : " + level);
      
      // 게시글 번호 가져오기
      var bno = '${ thumbnail.bno }';
      
      var parent = $(obj).parent();
      var grandparent = parent.parent();
      var siblingsTR = grandparent.siblings().last();
      
      var content = siblingsTR.find('textarea').val();
      
      // console.log(parent.html());
      // console.log(grandparent.html());
      // console.log(siblingsTR.html());
      
      // console.log(content);

      // writer, replyContent
      // bno, refcno, clevel
      
      location.href='/myWeb/insertComment.bo'
                 + '?writer=${ member.userId }' 
                 + '&replyContent=' + content
                 + '&bno=' + bno
                 + '&refcno=' + refcno
                 + '&clevel=' + level;
   }
   </script>
	<c:import url="../common/footer.jsp" />
</body>
</html>