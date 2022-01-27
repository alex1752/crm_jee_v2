<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Liste Utilisateur</title>
		<link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
		
	</head>
	<body>
				         
		<c:import url="/WEB-INF/menu.jsp"/>
		
		<div class="container my-4">
		
			<c:choose>
				<c:when test="${empty utilisateur}"><p>La liste des utilisateurs est vide !</p></c:when>
				
				<c:otherwise>
					 <table class="table table table-bordered"> <caption>Liste utilisateurs</caption>
			            <thead>   
			                <tr>
			                    <th scope="col">Login</th>
			                    <th scope="col">Email</th>
			                    <th scope="col">Action</th>
			                <tr>
			            </thead>
			
						<tbody>
				           <c:forEach items="${utilisateur}" var="utilisateur" varStatus="boucle">
				               <tr>
				                   <td> <a href="<c:url value="/DetailUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>"><c:out value="${utilisateur.login}"/></a></td>
				                   <td><c:out value="${utilisateur.email}"/></td>
				                   <td> <a href="<c:url value="/SupprimerUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>"><i class="las la-trash"></i></a>
				                   <a href="<c:url value="/ModifierUtilisateur"><c:param name="idUtilisateur" value="${utilisateur.id}"/></c:url>"><i class="las la-edit"></i></a>
				                   </td>
				               </tr>
							</c:forEach>
						</tbody>
			   	        </table>
	   	        </c:otherwise>
	   	    </c:choose>
		</div>
	</body>
</html>

