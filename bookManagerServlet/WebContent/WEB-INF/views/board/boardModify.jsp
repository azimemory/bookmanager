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
	<form  
		action="<%= request.getContextPath() %>/board/modifyimpl.do" 
		method="post" 
		enctype="multipart/form-data" id="frm">
	 	<div class="desc_board" >
	 		<div>
	 			<input type="hidden" name="userId" value="${logInInfo.userId}"/>
	 			<input type="hidden" name="bdIdx" value="${data.board.bdIdx}"/>
			    <div class="tit_board">
			          	제목 : <input type="text" name="title" value="${data.board.title}"/>
			          	파일업로드 : <input type="file" name="files" id="contract_file" multiple/>
		        </div>
		        <div class="info" style="height:auto; background-color:lightblue">
					<c:forEach items="${data.flist}" var="file">
						<button style="margin-left:1%;" type="button" onclick="deleteFile('${file.fIdx}')" id='f${file.fIdx}'>
						${file.originFileName} <i class="fas fa-times"></i></button>
					</c:forEach>
	      		</div>
		        <div class="text">
			      <textarea name="content" id="board-content"></textarea> 
			    </div>
		    </div>
		    <div class="btn_section" style="background-color:red">
	       	  <button style="color:white; text-align:center; font-size:1.5vw">전송</button>
	   	    </div>
		</div>
	</form>
</div>
<footer class="bottom">bottom</footer>
<script src = "<%= request.getContextPath() %>/resources/js/ckeditor/ckeditor.js"></script>	
<script type="text/javascript">

	CKEDITOR.replace('board-content',{
		//filebrowserUploadUrl:'/mine/imageUpload.do',
		height:300,
		enterMode:CKEDITOR.ENTER_BR
	});
	
	// 자바스크립트에서는 행 단위로 코드가 컴파일 되기 때문에 문자열을 여러행으로 쓸 수 없다. 
	// 백틱(`)을 통해 여러행의 문자열을 다룰 수 있다.
	CKEDITOR.instances['board-content'].setData(`${data.board.content}`);
 
	function deleteFile(fIdx){
		document.querySelector('#f'+fIdx).style.display='none';
		document.querySelector('#f'+fIdx).outerHTML += '<input type="hidden" name="rmFiles" value="'+fIdx+'">';
	}
</script>

</body>
</html>