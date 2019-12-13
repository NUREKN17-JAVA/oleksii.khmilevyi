<jsp:useBean id="user" class="ua.nure.kn.khmilevoi.usermanagement.User"
	scope="session"></jsp:useBean>
<html>
<head>
<title>User management: edit</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>>/edit" method="post">
		<input type="hidden" name="id" value="${user.id} }"> First
		name <input type="text" name="firstName" value="${user.firstName} }"><br>
		Last name <input type="text" name="lastName"
			value="${user.lastName} }"><br> Date of birth <input
			type="text" name="date"
			value="<fmt:formatDate value="${user.dateOfBirth}" type="date" dateStyle="medium"/>">
		<input type="submit" name="okButton" value="Confirm"> <input
			type="submit" name="cancelButton" value="Cancel">
	</form>
	<c:if test="${requestScope.error != null}">
		<script>
			alert('${requestScope.error}');
		</script>
	</c:if>
</body>
</html>