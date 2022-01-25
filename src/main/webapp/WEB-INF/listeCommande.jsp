<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>       
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Liste des commandes</title>
<link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
</head>
<body>
	<c:import url="/WEB-INF/menu.jsp"/>
	<table> 
        <thead> 
            <th>label</th>
            <th>tjmHT </th>
            <th> dureeJours</th>
            <th>TVA</th>
            <th> Statut</th>
            <th>TypeCommande</th>
            <th> notes </th>
            <th>Clients </th> 
            <th>Action</th> 
        </thead>

        <c:forEach items = "${ commandes }" var="commande" >
            <tr>
                <td><c:out value="${commande.label}"></c:out></td>
                <td><c:out value="${commande.tjmHT}"></c:out></td>
                <td><c:out value="${commande.dureeJours}"></c:out></td>
                <td><c:out value="${commande.TVA}"></c:out></td>
                <td><c:out value="${commande.statut}"></c:out></td>
                <td><c:out value="${commande.typeCommande}"></c:out></td>
                <td><c:out value="${commande.notes}"></c:out></td>
                <td><c:out value="${commande.client.nom}"></c:out></td>
                <td class = "action">
                    <a href="<c:url value="/SupprimerCommande"><c:param name="idCommande" value="${ commande.id }" /></c:url>"> 
                        <i class="las la-trash"></i>
                  	</a>
                 	<a href="<c:url value="/ModifierCommande"><c:param name="idCommande" value="${ commande.id }" /></c:url>"> 
                    	<i class="las la-edit"></i>
                  	</a>
                </td>
            </tr> 
        </c:forEach>
    </table>
</body>
</html>