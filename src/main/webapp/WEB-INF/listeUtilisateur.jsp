<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Liste Utilisateur</title>
	</head>
	<body>
				         
		<c:import url="/WEB-INF/menu.jsp"/>
		
		
		<c:choose>
			<c:when test="${empty utilisateur}"><p>La liste des utilisateurs est vide !</p></c:when>
			
			<c:otherwise>
				 <table class="blueTable"> <caption>Liste utilisateurs</caption>
		            <thead>   
		                <tr>
		                    <th>Login</th>
		                    <th>Email</th>
		                    <th>Action</th>
		                <tr>
		            </thead>
		
					<tbody>
			           <c:forEach items="${utilisateur}" var="utilisateur" varStatus="boucle">
			               <tr>
			                   <td> <a href="<c:url value="/DetailUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>"><c:out value="${utilisateur.login}"/></a></td>
			                   <td><c:out value="${utilisateur.email}"/></td>
			                   <td> <a href="<c:url value="/SupprimerUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>"><img src="<c:url value ="/assets/croix.png"/>" alt="supprimer"/></a>
			                   <a href="<c:url value="/ModifierUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>"><img src="<c:url value= "/assets/modifier.png"/>" alt="modifier"/></a>
			                   </td>
			               </tr>
						</c:forEach>
					</tbody>
		   	        </table>
   	        </c:otherwise>
   	    </c:choose>
	
	</body>
</html>

