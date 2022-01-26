<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<c:import url="/WEB-INF/menu.jsp"/>
		
		<h2>Fiche technique du client</h2>
		
		<div>
			<p>Nom : <c:out value="${client.nom}"/></p>
			<p>Prénom : <c:out value="${client.prenom}"/></p>
			<p>Adresse mail : <c:out value="${client.email}"/></p>
			<p>Numéro de téléphone : <c:out value="${client.telephone}"/></p>
			<p>Entreprise : <c:out value="${client.entreprise}"/></p>
			<p>Nombre de commandes : <c:out value="${nbcommandes}"/> commandes</p>

			<ul>
				<c:forEach items="${commandes}" var="commande" varStatus="boucle">
					<li> <c:out value="${commande.label}"/> statut : <c:out value="${commande.statut}"/></li>
				</c:forEach>
			</ul>
			<p>Notes client :<br/>
			<c:out value="${client.notes}"/></p>
			
		</div>

	</body>
</html>