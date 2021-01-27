<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/member.css" />
</head>

<body>
	<div class="box_member">
		<form action="" method="post" id="frm_member" class="login">
			<div class="login-info">
				<p>id</p>
				<input class="login-text" type="text" name="userId"/>
				<span id="checkMsg" class="checkMsg"></span>
			</div>
			<div class="login-info">
				<p>password</p>
				<input class="login-text" type="password" name="password"/>
			</div>
			<button class="btn_use btn_login" type="button" onclick="selectUrl('login')">Login</button>
			<button class="btn_use btn_signup" type="button" onclick="selectUrl('join')">회원가입</button>
		</form>
	</div>
<script>

function selectUrl(subType){
	
	var frm = document.querySelector('#frm_member');
	
	if(subType == 'login'){
		frm.action = '<%=request.getContextPath()%>/member/loginimpl.do';
	}else{
		frm.action = '<%=request.getContextPath()%>/member/join.do';
	}
	
	frm.submit();
}




</script>
</body>
</html>