<%@page import="model.StudentModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheets/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheets/footer.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheets/home.css" />
</head>
<body>
	<jsp:include page="<%=StringUtils.PAGE_URL_HEADER%>" />

	<h2>Welcome to our Home Page!</h2>

	<div class="students-info">
		<div class="users">
			<%
			ArrayList<StudentModel> students = (ArrayList<StudentModel>) request.getAttribute(StringUtils.STUDENT_LISTS);
			if (students.isEmpty()) {
			%>
			<p>No students found.</p>
			<%
			} else {
			for (StudentModel student : students) {
			%>
			<div class="card">
				<img
					src="resources/images/user/<%= student.getImageUrlFromPart() %>"
					class="card-img-top" alt="...">

				<div class="card-body">
					<h4 class="card-title">
						<%
						out.print(student.getFirstName());
						%>
						<%
						out.print(student.getLastName());
						%>
					</h4>
					<h5 class="card-text">
						<%
						out.print(student.getSubject());
						%>
					</h5>
				</div>
				<form method="post"
					action="${pageContext.request.contextPath}/ModifyServlet">
					<input type="hidden" name="updateId"
						value="<%out.print(student.getUsername());%>" />
					<button type="submit">Update</button>
				</form>
				<form id="deleteForm-<% out.print(student.getUsername());%>"
					method="post"
					action="${pageContext.request.contextPath}/ModifyServlet">
					<input type="hidden" name="deleteId"
						value="<%out.print(student.getUsername());%>" />
					<button type="button"
						onclick="confirmDelete('<%out.print(student.getUsername());%>')">Delete</button>
				</form>
			</div>
			<%
			}
			}
			%>
		</div>
	</div>

	<jsp:include page="<%=StringUtils.PAGE_URL_FOOTER%>" />
</body>
</html>
