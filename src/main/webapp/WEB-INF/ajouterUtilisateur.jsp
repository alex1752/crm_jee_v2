<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Ajout d'un utilisateur</title>
		<link type="text/css" rel="stylesheet"  href="<c:url value="assets/style.css?date=3"/>"/>		
	</head>
	<body>
		<c:import url="/WEB-INF/menu.jsp"/>
		<br/><h1>Ajout d'un utilisateur</h1><br/>
		
		<div>
			<form method="post" action="<c:url value="/AjouterUtilisateur"/>">
				<c:import url="/WEB-INF/formUtilisateur.jsp"/>
			</form>
		</div>
	
	</body>
</html>
