<%@page import="utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/stylesheets/register.css" />
</head>
<body>
	<div class="container">
		<h1>Registration Form</h1>
		<form action="<%=contextPath%>/registerstudent" method="post" enctype="multipart/form-data">
			<div class="row">
				<div class="col">
					<label for="firstName">First Name:</label> <input type="text"
						id="firstName" name="firstName" value="Prith" required>
				</div>
				<div class="col">
					<label for="lastName">Last Name:</label> <input type="text"
						id="lastName" name="lastName" value="Maha" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="username">Username:</label> <input type="text"
						id="username" name="username" value="uy1" required>
				</div>
				<div class="col">
					<label for="birthday">Birthday:</label> <input type="date"
						id="birthday" name="birthday" required>
				</div>

			</div>
			<div class="row">
				<div class="col">
					<label for="gender">Gender:</label> <select id="gender"
						name="gender" required>
						<option value="male">Male</option>
						<option value="female">Female</option>
					</select>
				</div>
				<div class="col">
					<label for="email">Email:</label> <input type="email" id="email"
						name="email" value="Prith@gmail.com" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="phoneNumber">Phone Number:</label> <input type="tel"
						id="phoneNumber" value="9898767654" name="phoneNumber" required>
				</div>
				<div class="col">
					<label for="subject">Subject:</label> <select id="subject"
						name="subject" required>
						<option value="computing">Computing</option>
						<option value="multimedia">Multimedia</option>
						<option value="networking">Networking</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="password">Password:</label> <input type="password"
						id="password" value="aaa" name="password" required>
				</div>
				<div class="col">
					<label for="retypePassword">Retype Password:</label> <input
						type="password" value="aaa" id="retypePassword" name="retypePassword" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="image">Profile Picture</label> 
					<input type="file"
						id="image" name="image">
				</div>
			</div>
			<button type="submit">Submit</button>
		</form>
		<%
		String errMsg = (String) request.getAttribute(StringUtils.MESSAGE_ERROR);
		String successMsg = (String) request.getAttribute(StringUtils.MESSAGE_SUCCESS);

		if (errMsg != null) {
			// print
		%>
		<h4 class="error-msg">
			<%
			out.println(errMsg);
			%>
		</h4>
		<%
		}

		if (successMsg != null) {
		// print
		%>
		<h4 class="success-msg">
			<%
			out.println(successMsg);
			%>
		</h4>
		<%
		}
		%>
	</div>
</body>
</html>
