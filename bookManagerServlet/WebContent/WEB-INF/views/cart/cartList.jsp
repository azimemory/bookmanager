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
	<h1>도서 장바구니</h1><hr>
	<button class="btn_rent-all" onclick="checkCartBook()">일괄 대출하기</button>
	<c:forEach items="${data}" var="book" varStatus="i">
	<div class="box_rent-detail" id="brd_${i.count}">
  	    <div class="book-detail">
  	    	<input type="checkbox" data-title="${book.title}" name="bIdx" class="chk_multi-rent" value="${book.bIdx}" id="cart-check" class="cart-check">
			<div class="img-book-detail">
				<img src="<%= request.getContextPath() %>/resources/image/book/${book.isbn}.jpg"/>
			</div>
			<div class="book-info">
				<div>
					<div class="info-col">도서명</div>
					<div class="info-val">${book.title}</div>
				</div>
				<div>
					<div class="info-col">작가</div>
					<div class="info-val">${book.author}</div>
				</div>
				<div>
					<div class="info-col">isbn</div>
					<div class="info-val">${book.isbn}</div>
				</div>
			</div>
			<c:choose>
			<c:when test="${book.bookAmt > 0}">
			<div class="wrap_use">
				<div>
					<button class="btn_extend" onclick="location.href='<%= request.getContextPath() %>/rent/rent.do?bIdx=${book.bIdx}&title=${book.title}'">대출하기</button>
				</div>
				<div>
					<button type="button" class="btn_return" onclick="deleteCart('${book.bcIdx}','brd_${i.count}')">삭제하기</button>
				</div>
			</div>
			</c:when>
		 	<c:otherwise>
		 	<div class="wrap_use">
		 		<div>
					<button class="btn_extend" onclick="alert('도서 재고가 부족합니다.')">대출불가</button>
				</div>
				<div>
					<button type="button" class="btn_return" onclick="deleteCart('${book.bcIdx}','brd_${i.count}')">삭제하기</button>
				</div>
			</div>
		 	</c:otherwise>
		 </c:choose>
		</div>
	</div>
	</c:forEach>
</div>
<footer class="bottom">bottom</footer>
<script type="text/javascript">
	
	let checkCnt = 0;
	
	function checkCartBook(){
		var sendData = {
			bIdxs : [],
			titles : []
		}
		
		document.querySelectorAll('.chk_multi-rent').forEach(function(v,i){
			if(v.checked){
				sendData.bIdxs.push(v.value);
				sendData.titles.push(v.dataset.title);
			}
		});	
		
		var xhr = new XMLHttpRequest();
		var url = '<%= request.getContextPath() %>/rent/multi.do';
		xhr.open('POST',url);
		xhr.setRequestHeader('Content-Type','application/json');
		xhr.send(JSON.stringify(sendData));
		xhr.addEventListener('load', function(){
			alert(xhr.response);
			location.href="<%= request.getContextPath() %>/rent/list.do";
		});
	}
	
	function deleteCart(bcIdx, id){
		var xhr = new XMLHttpRequest();
		var url = '<%=request.getContextPath() %>/cart/removeCart.do?bcIdx=' + bcIdx;
		xhr.open('GET',url);
		xhr.send();
		xhr.addEventListener('load', function(){
		console.dir(xhr);	
			var result = xhr.response;
			if(result == 'success'){
				alert('장바구니를 삭제하였습니다.');
				console.dir(document.querySelector('#'+id));
				document.querySelector('#'+id).outerHTML = '';
			}else if(result == 'fail'){
				alert('삭제도중 에러가 발생하였습니다.');
			}
		});
	}

</script>

</body>
</html>