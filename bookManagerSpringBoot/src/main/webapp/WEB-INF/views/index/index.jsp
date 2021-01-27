<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/header.jsp" %>
</head>
<body>
<%@include file="../include/top.jsp" %>
 <div class="content">
<%@include file="../include/floatDiv.jsp" %>
	<section class="middle"> 		
 		<div class="site-main">
			<p class="tit_site">U CLASS 도서관 </p>
			<form class="frm_site-search" action="<%=request.getContextPath()%>/book/search.do" class="frm_main-search" id="mainSearch" onsubmit="return index_validate()">
				<p class="btn-search">검색</p>
				<input Type ="text" name="keyword" placeholder=" 검색어를 입력하세요">
				<button value="" class="icon-search"><i class="fas fa-search" style="color:black"></i></button>
			</form>
 		</div>
		<nav class="gnb_menu">
			<div class="ps-1">
				<i class="fas fa-search-plus"></i>
				<p>도서검색</p>
				</div>
			<div class="ps-2">
				<a href="<%=request.getContextPath()%>/cart/cart.do"><i class="fas fa-cart-plus"></i>
				<p>장바구니</p></a>
				</div>
			<div class="ps-3">
				<a href="<%=request.getContextPath()%>/board/list.do"><i class="far fa-clipboard"></i>
				<p>공지사항</p></a>
				</div>
			<div class="ps-4">
				<i class="fas fa-users"></i>
				<p>AboutUs</p>
			</div>
		</nav>
 	</section>
 	<section class="wrap_book">
		<div class="box_tit_book"><span>최근 인기 도서</span></div>
		<div class="line_book">
		<c:forEach items="${books.recentPopular}" var="book">
			<div class="book-item">
				<div class="img_book">
					<a href="<%=request.getContextPath()%>/book/detail.do?bIdx=${book.bIdx}">
					<img src="<%=request.getContextPath()%>/resources/image/book/${book.isbn}.jpg">
					</a>
				</div>
				<div class="tit_book">${book.author}</div>
			</div>
		</c:forEach>
		</div>
		<div class="box_tit_book"><span>스테디셀러</span></div>
		<div class="line_book">
		<c:forEach items="${books.steadyBook}" var="book">
			<div class="book-item">
				<div class="img_book">
					<a href="<%=request.getContextPath()%>/book/detail.do?bIdx=${book.bIdx}">
					<img src="<%=request.getContextPath()%>/resources/image/book/${book.isbn}.jpg">
					</a>
				</div>
				<div class="tit_book">${book.author}</div>
			</div>
		</c:forEach>
		</div>
		<div class="box_tit_book"><span>새로 들어온 책</span></div>
		<div class="line_book">
		<c:forEach items="${books.recentInput}" var="book">
			<div class="book-item">
				<div class="img_book">
					<a href="<%=request.getContextPath()%>/book/detail.do?bIdx=${book.bIdx}">
					<img src="<%=request.getContextPath()%>/resources/image/book/${book.isbn}.jpg">
					</a>
				</div>
				<div class="tit_book">${book.author}</div>
			</div>
		</c:forEach>
		</div>
		<div class="box_tit_book"><span>최근 대출 도서</span></div>
		<div class="line_book">
		<c:forEach items="${books.recentRent}" var="book">
			<div class="book-item">
				<div class="img_book">
					<a href="<%=request.getContextPath()%>/book/detail.do?bIdx=${book.bIdx}">
					<img src="<%=request.getContextPath()%>/resources/image/book/${book.isbn}.jpg">
					</a>
				</div>
				<div class="tit_book">${book.author}</div>
			</div>
		</c:forEach>
		</div>
	 </section>
</div>
<footer class="bottom">bottom</footer>
<script type="text/javascript">
	function index_validate(){
		if(mainSearch.value == ''){
		alert('검색어를 입력해주세요.');
			return false;
		}
		return true;
	}
</script>
</body>
</html>
