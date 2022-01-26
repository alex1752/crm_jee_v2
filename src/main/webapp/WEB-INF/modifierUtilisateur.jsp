<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Modifier utilisateur</title>
		<link type="text/css" rel="stylesheet"  href="<c:url value="assets/style.css?date=3"/>"/>		
	</head>
	<body>
	
		<c:import url="/WEB-INF/menu.jsp"/>
		
		<div>
            <form action="<c:url value="/ModifierUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>" method="post" class="creation">
                <c:import url="/WEB-INF/formUtilisateur.jsp"/>
            </form>
    	</div>
	
	</body>
</html>