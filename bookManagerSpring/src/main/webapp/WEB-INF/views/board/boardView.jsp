<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/board.css" />
</head>
<body>
<%@include file="../include/top.jsp" %>
<%@include file="../include/floatDiv.jsp" %>
<div class="content">   
    <h2 class="tit">*게시판</h2>
    <div class="desc_board">
		<h4 class="tit_board">${data.board.title}</h4>
		<div class="info" >
		    <span>게시글번호 : ${data.board.bdIdx}</span>
		    <span>등록일 : ${data.board.regDate}</span>
		    <span>작성자 : ${data.board.userId}</span>
		</div>
		<div class="info">
		<c:forEach items="${data.flist}" var="file">
		   <button type="button" class="btn_down-file"
		         onclick="downloadFile('${file.originFileName}','${file.savePath}')">
		     	     파일 이름 : ${file.originFileName}
		   </button>
		</c:forEach>
		</div>
		<div class="text">
		     ${data.board.content}
		</div>
		<div class="btn_section btn_list">
		    <button style="color:white" onclick="submitData('list.do')"><span>목록</span></button>
		</div>
		<c:if test="${logInInfo.userId == data.board.userId}">
		 <div class="btn_section btn_delete">
		  <button style="color:white" onclick="submitData('delete.do?bdIdx=${data.board.bdIdx}&userId=${data.board.userId}')"><span>삭제</span></button>
		</div>
		<div class="btn_section btn_modify">
		  <button style="color:white" onclick="submitData('modify.do?bdIdx=${data.board.bdIdx}&userId=${data.board.userId}')"><span>수정</span></button>
		</div>
		</c:if>
   </div>
</div>
<footer class="bottom">bottom</footer>
<script type="text/javascript">
   function submitData(url){
      location.href = url;
   } 
   
   function downloadFile(ofname, savePath){
      location.href = "download.do?"
            +"ofname="+ofname
            +"&savePath="+savePath 
     /* location.href = "/bookmanager/resources/upload/"+savePath;  */
  }

</script>










</body>
</html>