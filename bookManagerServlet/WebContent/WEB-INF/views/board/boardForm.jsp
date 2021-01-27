<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/board.css" />
</head>
<body>
<!-- 에디터 영역 생성 -->
<%@include file="../include/top.jsp" %>
<%@include file="../include/floatDiv.jsp" %>
<div class="content">
	<h2 class="tit">*게시판</h2>
	<!--
		Request Message Header 
		Content-Type : multipart/form-data 
		여러 종류의 데이터가 동시에 서버에게 보내질 때 사용
	-->
 	<div class="desc_board">
 		<form action="<%= request.getContextPath() %>/board/upload.do" method="post" enctype="multipart/form-data">
	 		<div>
			    <div class="tit_board">
		          	제목 : <input type="text" name="title"/>
		          	<!-- multiple : 여러개의 파일 선택을 허용하는 속성 -->
		          	파일업로드 : <input type="file" name="files" id="contract_file" multiple/>
		        </div>
		        <div class="text">
			      	<textarea id="board-content" class="board-content" name="content"></textarea> 
			    </div>
		    </div>
		    <div class="btn_section" style="background-color:red">
	       	  <button  onclick="ckeGetData()" style="color:white; text-align:center; font-size:1.5vw">전송</button>
	   	    </div>
   	   </form>
	</div>
</div>
<footer class="bottom">bottom</footer>
<script src = "<%= request.getContextPath() %>/resources/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	CKEDITOR.replace('board-content',{
		//filebrowserUploadUrl:'/mine/imageUpload.do',
		height:300,
		enterMode:CKEDITOR.ENTER_BR
	});
	
	function ckeGetData(){
		document.querySelector('#board-content').value = CKEDITOR.instances['board-content'].getData();
	}
</script>
</body>
</html>