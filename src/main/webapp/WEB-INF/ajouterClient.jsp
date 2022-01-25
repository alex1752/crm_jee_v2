<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Ajouter Client</title>
	</head>
	<body>
		<c:import url="/WEB-INF/menu.jsp"/>
		
		<div>
            <form action="<c:url value="/AjouterClient"/>" method="post" class="creation">
                <c:import url="/WEB-INF/formClient.jsp"/>
            </form>
    	</div>
	</body>
</html>