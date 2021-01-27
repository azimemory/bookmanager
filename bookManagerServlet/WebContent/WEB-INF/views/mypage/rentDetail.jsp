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
	<h1>대출 도서 리스트</h1><hr>
	<c:forEach items="${data}" var="rent" varStatus="i">
	<div class="box_rent-detail">
  	    <div class="book-detail">
			<div class="img-book-detail">
				<img src="<%= request.getContextPath() %>/resources/image/book/${rent.isbn}.jpg"/>
			</div>
			<div class="book-info">
				<div>
					<div class="info-col">도서명</div>
					<div class="info-val">${rent.title}</div>
				</div>
				<div>
					<div class="info-col">대출일</div>
					<div class="info-val">${rent.regDate}</div>
				</div>
				<div>
					<div class="info-col">반납일</div>
					<div class="info-val">${rent.returnDate}</div>
				</div>
			</div>
			<form method="post" id="form-data-${i.count}" class="frm_use wrap_use">
				<input type="hidden" name="rmIdx" value="${rent.rmIdx}"/>
				<input type="hidden" name="rbIdx" value="${rent.rbIdx}"/>
			<c:if test="${rent.state == 'RE00' or rent.state == 'RE01'}">
				<div>
					<button class="btn_extend" onclick="selectUrl('form-data-${i.count}','/rent/extend.do')">연장하기</button>
				</div>
				<div>
					<button class="btn_return" onclick="selectUrl('form-data-${i.count}','/rent/return.do')">반납하기</button>
				</div>
			</c:if>
			<c:if test="${rent.state == 'RE02'}">
				<div>
					<button type="button" class="btn_extend" onclick="alert('연체기간에는 연장할 수 없습니다.')">연장하기</button>
				</div>
				<div>
					<button class="btn_return" onclick="selectUrl('form-data-${i.count}','/rent/return.do')">반납하기</button>
				</div>
			</c:if>
			<c:if test="${rent.state == 'RE03'}">
				<div>
					<button type="button" class="btn_end" onclick="alert('반납처리된 도서입니다.')">반납완료</button>
				</div>
			</c:if>
			</form>
		</div>
	</div>
	</c:forEach>
</div>
<script type="text/javascript">
	function selectUrl(frm,url){
		var root = '<%=request.getContextPath()%>';
		document.querySelector('#'+frm).action = root + url;
	}
</script>
</body>
</html>