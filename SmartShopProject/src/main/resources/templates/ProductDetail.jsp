<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<td>Image</td>
			<td>Product Name</td>
			<td>Price</td>
			<td>Description</td>
			<td>Quatity</td>
			<td>Category Name</td>
			<td>Status</td>
		</tr>
		<c:forEach items="${product}" var="p">
			<tr>
				<td>${p.imageURL}</td>
				<td>${p.productName}</td>
				<td>${p.price}</td>
				<td>${p.description}</td>
				<td>${p.quatity}</td>
				<td>${p.categoryName}</td>
				<td>${p.status}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>