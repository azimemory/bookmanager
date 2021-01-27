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
	  	   <table class="list_board">
		       <thead>
		           <tr>
		               <th style="width: 10%; height:20%;"><span>번호</span></th>
		               <th style="width: 70%;"><span>제목</span></th>
		               <th style="width: 10%;"><span>작성자</span></th>
		               <th style="width: 10%;"><span>등록일</span></th>
		           </tr>
		       </thead>
		       <tbody>
		       <c:forEach items="${data.blist}" var="board">
		           <tr>
		               <td class="num">${board.bdIdx}</td>
		               <td class="subject"><a href="<%= request.getContextPath() %>/board/detail.do?bdIdx=${board.bdIdx}">${board.title}</a></td>
		               <td class="date">${board.userId}</td>
		               <td class="date">${board.regDate}</td>
		           </tr>
		        </c:forEach>
		       </tbody>
		       </table>
	       <div class="paging"><!-- section pagination -->
	         <a href="<%= request.getContextPath() %>/board/list.do" class="nav first"><i class="fas fa-angle-double-left"></i></a>
	        <c:choose>
	        	<c:when test="${paging.blockStart > 1 }">
	         		<a href="<%= request.getContextPath() %>/board/list.do?cPage=${paging.blockStart-1}" class="nav prev"><i class="fas fa-angle-left"></i></a>
	        	</c:when>
	        	<c:otherwise>
	        		<a href="<%= request.getContextPath() %>/board/list.do?cPage=${paging.blockStart}" class="nav prev"><i class="fas fa-angle-left"></i></a>
	        	</c:otherwise>
	        </c:choose>
	        <c:forEach begin="${paging.blockStart}" end="${paging.blockEnd}" var="page">
	         <a href="<%= request.getContextPath() %>/board/list.do?cPage=${page}" class="num active"><span>${page}</span></a>
	        </c:forEach> 
	        
	        <c:choose>
	        	<c:when test="${paging.blockEnd+1 > paging.lastPage }">
	         		<a href="<%= request.getContextPath() %>/board/list.do?cPage=${paging.blockEnd}" class="nav next"><i class="fas fa-angle-right"></i></a>
	        	</c:when>
	        	<c:otherwise>
	         		<a href="<%= request.getContextPath() %>/board/list.do?cPage=${paging.blockEnd+1}" class="nav next"><i class="fas fa-angle-right"></i></a>
	        	</c:otherwise>
	   	   	</c:choose>
	 	   	 
	 	   	 <a href="<%= request.getContextPath() %>/board/list.do?cPage=${paging.lastPage}" class="nav last"><i class="fas fa-angle-double-right"></i></a>
	      	<div class="btn_section" style="background-color:white">
	      	 <a href="<%= request.getContextPath() %>/board/write.do">쓰기</a>
	   	  	</div>
	   	   </div><!-- // section pagination -->
	     </div>
	<footer class="bottom">bottom</footer>   
</body>
</html>