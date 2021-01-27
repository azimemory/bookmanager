<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/rent.css" />
</head>
<body>
<%@include file="../include/top.jsp" %>
<%@include file="../include/floatDiv.jsp" %>
<div class="content">
	<div class="responsive-box-menu">
		<div class="box_menu">
		</div>
	</div>
	<h1>최근 대출 목록</h1>
	<hr>
	<div class="search-box">
  	   <table class="list-rent">
       <thead>
           <tr>
               <th style="width: 10%; height:20%;"><span>대출번호</span></th>
               <th style="width: 60%;"><span>대출건 제목</span></th>
               <th style="width: 20%;"><span>대출일</span></th>
               <th style="width: 10%;"><span>완납여부</span></th>
           </tr>
       </thead>
       <tbody>
       <c:forEach items="${data}" var="rent">
           <tr>
               <td>
                   <strong>${rent.rmIdx }</strong>
               </td>
               <td>
              	  <a href="<%=request.getContextPath() %>/rent/detail.do?rmIdx=${rent.rmIdx}">
               	   ${rent.title }</a>
              </td>
               <td>${rent.regDate}</td>
               <c:if test="${rent.isReturn == 0}">
               	<td>N</td>
               </c:if>
               <c:if test="${rent.isReturn == 1}">
               	<td>Y</td>
               </c:if>
               <td>
               </td>
               <td>
               </td>
           </tr>
        </c:forEach>
       </tbody>
       </table>
       <div class="paging"><!-- section pagination -->
         <a href="<%= request.getContextPath() %>/rent/list.do?" class="nav first"><i class="fas fa-angle-double-left"></i></a>
        <c:choose>
        	<c:when test="${paging.blockStart > 1 }">
         		<a href="<%= request.getContextPath() %>/rent/list.do?cPage=${paging.blockStart-1}" class="nav prev"><i class="fas fa-angle-left"></i></a>
        	</c:when>
        	<c:otherwise>
        		<a href="<%= request.getContextPath() %>/rent/list.do?cPage=${paging.blockStart}" class="nav prev"><i class="fas fa-angle-left"></i></a>
        	</c:otherwise>
        </c:choose>
        <c:forEach begin="${paging.blockStart}" end="${paging.blockEnd}" var="page">
         <a href="<%= request.getContextPath() %>/rent/list.do?cPage=${page}" class="num active"><span>${page}</span></a>
        </c:forEach> 
        
        <c:choose>
        	<c:when test="${paging.blockEnd+1 > paging.lastPage }">
         		<a href="<%= request.getContextPath() %>/rent/list.do?cPage=${paging.blockEnd}" class="nav next"><i class="fas fa-angle-right"></i></a>
        	</c:when>
        	<c:otherwise>
         		<a href="<%= request.getContextPath() %>/rent/list.do?cPage=${paging.blockEnd+1}" class="nav next"><i class="fas fa-angle-right"></i></a>
        	</c:otherwise>
   	   	</c:choose>
 	   	 
 	   	 <a href="<%= request.getContextPath() %>/rent/list.do?cPage=${paging.lastPage}" class="nav last"><i class="fas fa-angle-double-right"></i></a>
   	   </div><!-- // section pagination -->
</div>
</div>
<footer class="bottom">bottom</footer>
</body>
</html>