<%@page import="model.StudentModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/stylesheets/header.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/stylesheets/footer.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/stylesheets/home.css" />
</head>
<body>
	<jsp:include page="<%=StringUtils.PAGE_URL_HEADER%>" />
	<h2>Welcome to our Home Page!</h2>

	<div class="students-info">
		<div class="users">

			<c:if test="${empty studentLists}">
				<p>No students found.</p>
			</c:if>

			<c:if test="${not empty studentLists}">
				<c:forEach var="student" items="${studentLists}">
					<div class="card">
						<img src="resources/images/user/${student.imageUrlFromPart}"
							class="card-img-top" alt="...">
						<div class="card-body">
							<h4 class="card-title">${student.firstName}
								${student.lastName}</h4>
							<h5 class="card-text">${student.subject}</h5>
						</div>
						<form method="post"
							action="<%=contextPath + StringUtils.SERVLET_URL_MODIFY_USER%>">
							<input type="hidden" name="<%=StringUtils.UPDATE_ID %>" value="${student.username}" />
							<button type="submit">Update</button>
						</form>
						<form id="deleteForm-${student.username}" method="post"
							action="<%=contextPath + StringUtils.SERVLET_URL_MODIFY_USER %>">
							<input type="hidden" name="<%=StringUtils.DELETE_ID %>" value="${student.username}" />
							<button type="button"
								onclick="confirmDelete('${student.username}')">Delete</button>
						</form>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>

	<jsp:include page="<%=StringUtils.PAGE_URL_FOOTER%>" />
</body>


<script>
	function confirmDelete(userName) {
		if (confirm("Are you sure you want to delete this user: " + userName
				+ "?")) {
			document.getElementById("deleteForm-" + userName).submit();
		}
	}
</script>
</html>