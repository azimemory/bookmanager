<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../include/header.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/mypage.css" />
</head>
<body>
<%@include file="../include/top.jsp" %>
<aside class="wrap_float-div">
	<div class="float-div"><!-- lnb -->
        <span>Menu</span>
        <ul class="fdlist"><!-- d1 -->
           <li class="d1">
        		<a href="<%=request.getContextPath()%>/index/index.do">
	            	<i class="fas fa-search-plus"></i>
	                <span>도서검색</span>
                </a>
			</li>
			<li class="d1">
				<a href="<%=request.getContextPath()%>/cart/cart.do">
					<i class="fas fa-cart-plus"></i>
	                <span>장바구니</span>
                </a>
			</li>
            <li class="d1">
	            <a href="<%=request.getContextPath()%>/notice/list.do">
	              <i class="far fa-clipboard"></i>
	              <span>게시판</span>
               	</a>
            </li>
            <li class="d1">
            	<a href="<%=request.getContextPath()%>/rent/list.do">
            	<i class="fas fa-users"></i>
                <span>대출내역</span>
                </a>
            </li>
        </ul><!-- // d1 -->
    </div>
</aside> 
<div class="content">	
	<form id="form-data" class="frm_my" action="" method="POST">
		<h1>MyPage</h1><hr>
		<div class="my-info">
			<div style="text-align:left">ID :</div>
			<div>${logInInfo.userId}</div>
		</div>
		<div class="my-info">
			<div style="text-align:left">Password :</div>
			<div><input name="password" style="width:100%" type="password" value="${logInInfo.password}"></div>
		</div>
		<div class="my-info">
			<div style="text-align:left">Email :</div>
			<div><input name="email" style="width:100%" type="text" value="${logInInfo.email}"></div>
		</div>
		<div class="my-info">
			<div style="text-align:left">Tell :</div>
			<div><input name="tell" style="width:100%" type="text" value="${logInInfo.tell}"></div>
		</div>	
		<div class="wrap_btn">
			<div class="btn_modify">
				<button onclick="selectUrl('/mypage/modify.do')">수정하기</button>
			</div>
			<div class="btn_leave">
				<button onclick="selectUrl('/mypage/leave.do')">탈퇴하기</button>
			</div>
		</div>
	</form>
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