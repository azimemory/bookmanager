<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<header class="header-section">
 		<div class="top">
			<a class="top-logo-text" href="<%= request.getContextPath() %>/index/index.do">UCLASS</a>
				<form action="<%=request.getContextPath()%>/book/search.do" class="frm_top-search" onsubmit="return top_validate()">
					<input Type ="text" class="input-top-search" name="keyword" id="topSearch" placeholder="검색어를 입력하세요"/>
					<button value="" class="btn-top-search"><i class="fas fa-search" style="color:black"></i></button>
				</form>
			<c:if test="${sessionScope.logInInfo == null}">
				<a class="top-user-join" href="<%=request.getContextPath()%>/member/join.do">회원가입</a>
				<a class="top-user-login" href="<%=request.getContextPath()%>/member/login.do">Login</a>
			</c:if>
			<c:if test="${sessionScope.logInInfo != null}">
				<a class="top-user-join" href="<%=request.getContextPath()%>/member/logout.do">Logout</a>
				<a class="top-user-login" href="<%=request.getContextPath()%>/mypage/mypage.do">MyPage</a>
			</c:if>
 		</div>
 		<script type="text/javascript">
 			function top_validate(){
 				if(topSearch.value == ''){
					alert('검색어를 입력해주세요.');
					return false;
 				}
 				
 				return true;
 			}
 				
 		</script>
	</header>