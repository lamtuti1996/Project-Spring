<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%-- 	<c:if test="${param.error != null}">
		<p>Invalid email or password
		<p>
	</c:if> --%>
	<c:if test="${param.logout}">
		<p>Invalid email or password
		<p>
	</c:if>
	<c:url value="/login" var="login"/>
	<form action="${login}" method="POST">
		<input type="text" name="email" /><br /> <input type="password"
			name="password" /><br />
		<button type="submit">Login</button>
		<br />
	</form>
</body>
</html>