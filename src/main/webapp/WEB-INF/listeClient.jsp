<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Liste Clients</title>
	</head>
	
	<body>
		<c:import url="/WEB-INF/menu.jsp"/>
		<c:choose>
			<c:when test="${empty clients}"><p>La liste des Clients est vide !</p></c:when>
			
			<c:otherwise>
				 <table class="blueTable"> <caption>Liste clients</caption>
		            <thead>   
		                <tr>
		                    <th>Nom</th>
		                    <th>Prénom</th>
		                    <th>Entreprise</th>
		                    <th>Téléphone</th>
		                    <th>Email</th>
		                    <th>Actif</th>
		                    <th>Note</th>
		                   	<th>Action</th>
		                <tr>
		            </thead>
		
		           <c:forEach items="${clients}" var="client" varStatus="boucle">
		               <tr>
		                   <td> <a href="<c:url value="/detailClient"><c:param name="idClient" value="${client.id}"/></c:url>"><c:out value="${client.nom}"/></a></td>
		                   <td><c:out value="${client.prenom}"/></td>
		                   <td><c:out value="${client.entreprise}"/></td>
		                   <td><c:out value="${client.telephone}"/></td>
		                   <td><c:out value="${client.email}"/></td>
		                   <td><c:out value="${client.actif}"/></td>
		                   <td><c:out value="${client.note}"/></td>
		                   <td> <a href="<c:url value="/suppressionClient"><c:param name="idClient" value="${client.id}"/></c:url>"><img src="<c:url value ="/assets/croix.png"/>" alt="supprimer"/></a>
		                   <a href="<c:url value="/modifierClient"><c:param name="idClient" value="${client.id}"/></c:url>"><img src="<c:url value= "/assets/modifier.png"/>" alt="modifier"/></a>
		                   </td>
		               </tr>
					</c:forEach>	
		   	        </table>
   	        </c:otherwise>
   	    </c:choose>
		
	</body>
</html>