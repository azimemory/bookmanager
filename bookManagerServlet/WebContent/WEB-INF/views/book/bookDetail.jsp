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
	<div class="section_book">
		<h1>도서검색결과</h1><hr>
		<div class="book-detail">
			<div class="img-book-detail">
				<img src="<%= request.getContextPath() %>/resources/image/book/${book.isbn}.jpg"/>
			</div>
			<form action=""  id="form-data" class="wrap_book-info" method="post">
				<input type="hidden" name="bIdx" value="${book.bIdx}">
				<input type="hidden" name="title" value="${book.title}">
				<div class="book-info">
					<div class="info-col">ISBN코드</div>
					<div class="info-val">${book.isbn}</div>
				</div>
				<div class="book-info">
					<div class="info-col">도서명</div>
					<div class="info-val">${book.title}</div>
				</div>
				<div class="book-info">
					<div class="info-col">작가</div>
					<div class="info-val">${book.author}</div>
				</div>
				<div class="book-info">
					<div class="info-col">대출횟수</div>
					<div class="info-val">${book.rentCnt}</div>
				</div>
				<div class="book-info">
					<div class="info-col">도서 재고</div>
					<div class="info-val">${book.bookAmt}</div>
				</div>
		 <c:choose>
		 	<c:when test="${book.bookAmt > 0}">
					<button class="btn_use btn_rent" onclick="selectUrl('/rent/rent.do')">대출하기</button>
					<button class="btn_use btn_cart" onclick="selectUrl('/cart/addCart.do')">장바구니</button>
		 	</c:when>
		 	<c:otherwise>
					<button class="btn_use btn_rent-next">다음 기회에</button>
					<button class="btn_use btn_reserve">예약하기</button>
		 	</c:otherwise>
		 </c:choose>
			</form>
		</div>
	</div>
</div>
<footer class="bottom">bottom</footer>
<script type="text/javascript">
	function selectUrl(url){
		var root = '<%=request.getContextPath()%>';
		document.querySelector('#form-data').action = root + url;
	}
</script>
</body>
</html>