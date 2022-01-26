<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Modifier clients</title>
	</head>
	<body>
	<c:import url="/WEB-INF/menu.jsp"/>

		<div>
	       <form action="<c:url value="/ModifierCommande"><c:param name="idCommande" value="${commande.id}"/></c:url>" method="post" class="creation">
	           <c:import url="/WEB-INF/formCommande.jsp"/>
	       </form>
	   	</div>
	
	</body>
</html>