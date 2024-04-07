<%@page import="model.StudentModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

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
	
	<sql:setDataSource var="dbConnection" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/student_management" user="root"
		password="" />

	<sql:query var="students" dataSource="${dbConnection}">
	SELECT first_name, last_name, user_name FROM student_info WHERE user_name LIKE "%a%"
	</sql:query>

	<div class="students-info">
		<div class="users">
			<c:forEach var="student" items="${students.rows}">
				<div class="card">
					<img src="http://localhost:8080/images/${student.image}"
						class="card-img-top" alt="...">
					<div class="card-body">
						<h4 class="card-title">${student.first_name}
							${student.last_name}</h4>
						<h5 class="card-text">${student.subject}</h5>
					</div>
					<form method="post"
						action="${pageContext.request.contextPath}/ModifyServlet">
						<input type="hidden" name="updateId" value="${student.user_name}" />
						<button type="submit">Update</button>
					</form>
					<form id="deleteForm-${student.user_name}" method="post"
						action="${pageContext.request.contextPath}/ModifyServlet">
						<input type="hidden" name="deleteId" value="${student.user_name}" />
						<button type="button"
							onclick="confirmDelete('${student.user_name}')">Delete</button>
					</form>
				</div>
				
			</c:forEach>
		</div>
	</div>

	<jsp:include page="<%=StringUtils.PAGE_URL_FOOTER%>" />
</body>
</html>
