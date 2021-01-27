<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에러 페이지</title>
</head>
<body>

	<h1>404 에러가 발생했군요!</h1>
	<% if(exception != null){ %>
		<%= exception.getMessage() %>	
	<%} %>
	<br>
	<img src="/jspPJ/resources/img/kimgoeun.jpg">
		
















</body>
</html>



