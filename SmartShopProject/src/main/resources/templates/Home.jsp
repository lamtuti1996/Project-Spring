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
			<td>Detail</td>
		</tr>
		<c:forEach items="${listProduct}" var="list">
			<tr>
				<td>${list.imageURL}</td>
				<td>${list.productName}</td>
				<td>${list.price}</td>
				<td>${list.description}</td>
				<td><a
					href="<c:url value='/searchProduct/${user.productID}' />">Click</a></td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<ul class='pagination pagination-centered'>
			<li class="${page.firstPage}?'disabled':''"><c:if
					test="${page.firstPage}">
					<span>Fist</span>
				</c:if> <c:if test="${not page.firstPage}">
					<a href='@{${page.url}(page=0,size=${page.size})}'>First </a>
				</c:if></li>


			<li class="${page.hasPreviousPage}? 'active' : 'disabled'"><c:if
					test="${not page.hasPreviousPage}">
					<span>Ã‚Â«</span>
				</c:if> <c:if test="${page.hasPreviousPage}">
					<a href='@{${page.url}(page=${page.number-2},size=${page.size})}'
						title='Go to previous page'>«</a>
				</c:if></li>
			<c:forEach var="item" items="${page.items}">
				<li class="${item.current}? 'active' : ''"><c:if
						test='${item.current}'>
						<span>${item.number}</span>
					</c:if> <c:if test='${not item.current}'>
						<a href='@{${page.url}(page=${item.number-1},size=${page.size})}'>
							<span>${item.number}</span>

						</a>
					</c:if></li>
			</c:forEach>
			<li class="${page.hasNextPage}? 'active' : 'disabled'"><c:if
					test='${not page.hasNextPage}'>
					<span>Ã‚Â»</span>
				</c:if> <c:if test='${page.hasNextPage}'>
					<a href='@{${page.url}(page=${page.number},size=${page.size})}'
						title='Go to next page'>»</a>
				</c:if></li>
			<li class="${page.lastPage}? 'disabled' : ''"><c:if
					test='${page.lastPage}'>
					<span>Last </span>
				</c:if> <c:if test='${not page.lastPage}'>
					<a
						href='@{${page.url}(page=${page.totalPages - 1},size=${page.size})}'>Last</a>
				</c:if></li>
		</ul>
	</div>


</body>
</html>