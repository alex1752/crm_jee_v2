<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>

		<html>

		<head>
			<meta charset="ISO-8859-1">
			<title>Liste Clients</title>
		</head>

		<body>

			<c:import url="/WEB-INF/menu.jsp" />
			<c:choose>
				<c:when test="${empty client}">
					<p>La liste des Clients est vide !</p>
				</c:when>

				<c:otherwise>
					<div class="container my-4">

						<table class="text-center blueTable">
							<caption>Liste clients</caption>
							<thead>
								<tr>
									<th scope="col">Nom</th>
									<th scope="col">Pr�nom</th>
									<th scope="col">Entreprise</th>
									<th scope="col">T�l�phone</th>
									<th scope="col">Email</th>
									<th scope="col">Actif</th>
									<th scope="col">Note</th>
									<th scope="col">Action</th>
								<tr>
							</thead>

							<c:forEach items="${client}" var="client" varStatus="boucle">
								<tr>
									<th scope="row"> <a href="<c:url value=" /DetailClient">
											<c:param name="idClient" value="${client.id}" />
											</c:url>">
											<c:out value="${client.nom}" />
										</a></td>
									<td>
										<c:out value="${client.prenom}" />
									</td>
									<td>
										<c:out value="${client.entreprise}" />
									</td>
									<td>
										<c:out value="${client.telephone}" />
									</td>
									<td>
										<c:out value="${client.email}" />
									</td>
									<td>
										<c:out value="${client.actif}" />
									</td>
									<td>
										<c:out value="${client.notes}" />
									</td>
									<td> <a href="<c:url value=" /SupprimerClient">
											<c:param name="idClient" value="${client.id}" />
											</c:url>"><img src="<c:url value =" /assets/croix.png" />" alt="supprimer"/>
										</a>
										<a href="<c:url value=" /ModifierClient">
											<c:param name="idClient" value="${client.id}" />
											</c:url>"><img src="<c:url value= " /assets/modifier.png" />"
											alt="modifier"/>
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>

					</div>
				</c:otherwise>
			</c:choose>

		</body>

		</html>