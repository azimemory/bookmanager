<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/book.css" />
</head>
<body>
<%@include file="../include/top.jsp" %>
<%@include file="../include/floatDiv.jsp" %>
 <div class="content">
	<section class="section_book">
		<form class="frm-content" action="<%= request.getContextPath() %>/book/innersearch.do" method="post">
			<div class="box_menu">
				<div>
					<span>검색 결과 내 검색 : </span><input type="text" name="title"/>
				</div>
				<div>
					<span>인기순 정렬 : </span><input type="radio" name="orderBy" value="B_RENT_CNT"/>
				</div>
				<div>
					<span>최근 도서 순 정렬 : </span><input type="radio" name="orderBy" value="B_REGDATE"/>
				</div>
				<div>
					<button class="btn-search">검색</button>
				</div>
			</div>
			<h2>도서검색결과</h2>
			<hr>
			<div class="box_book">
				<div class="line_book">
				<!-- <li>번호: ${status.index}, 순번: ${status.count}, 책 이름 : ${bookTitle}</li> -->
				<c:forEach items="${bookList}" var="book" varStatus="status">
					<input type="hidden" value="${book.bIdx}" name="bIdxList"/>
					<c:if test="${status.count %5 != 0 }">
					<div class="book-item">
						<div>
							<a href="<%=request.getContextPath()%>/book/detail.do?bIdx=${book.bIdx}">
							<img src="<%= request.getContextPath() %>/resources/image/book/${book.isbn}.jpg">
							</a>
						</div>
						<div class="tit_book">작가:${book.author}</div>
					</div>
					</c:if>
					<c:if test="${status.count%5 == 0 }">
						<div class="book-item">
							<div>
								<a href="<%=request.getContextPath()%>/book/detail.do?bIdx=${book.bIdx}">
								<img src="<%= request.getContextPath() %>/resources/image/book/${book.isbn}.jpg">
								</a>
							</div>
							<div class="tit_book">작가:${book.author}</div>
						</div>
					</div>
					<div class="line_book">
					</c:if>
				</c:forEach>
				</div>
			</div>
		</form>
 	</section>
</div>		
<footer class="bottom">
	bottom
</footer>
	
<script>
	document.querySelectorAll('.productItem').forEach(function(v){
		v.addEventListener('mouseover',function(){
			v.lastElementChild.style.display = 'block';
		})
		
		v.addEventListener('mouseout',function(){
			v.lastElementChild.style.display = 'none';
		})
	})
</script>

</body>
</html>