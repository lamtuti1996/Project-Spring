<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form:form action="/AssignSpringMVC/checkLogin.html" modelAttribute="user">
		<div>User Name</div>
		<form:input path="username" />
		<form:errors path="username" />
		<div>Password</div>
		<form:input path="password" />
		<form:errors path="password" />
		<button>Login</button>
	</form:form>
	${message} 
</body>
</html>