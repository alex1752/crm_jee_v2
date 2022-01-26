<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="ISO-8859-1">
			<title>Modifier Client</title>
		</head>

		<body>
			<c:import url="/WEB-INF/menu.jsp" />

			<div>
				<form action="<c:url value="/ModifierClient">
					<c:param name="idClient" value="${client.id}" />
					</c:url>" method="post" class="creation">
					<c:import url="/WEB-INF/formClient.jsp" />
				</form>
			</div>
		</body>

		</html>