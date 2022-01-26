<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="ISO-8859-1">
            <title>Liste des commandes</title>
            <link rel="stylesheet"
                href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        </head>

        <body>
            <c:import url="/WEB-INF/menu.jsp" />

            <div class="container my-4">

                <table class="text-center">
                    <thead>
                        <tr>
                            <th scope="col">label</th>
                            <th scope="col">tjmHT </th>
                            <th scope="col">dureeJours</th>
                            <th scope="col">TVA</th>
                            <th scope="col">Statut</th>
                            <th scope="col">TypeCommande</th>
                            <th scope="col">notes</th>
                            <th scope="col">Clients</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>

                    <c:forEach items="${ commandes }" var="commande">
                        <tr>
                            <th scope="row">
                                <c:out value="${commande.label}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.tjmHT}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.dureeJours}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.TVA}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.statut}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.typeCommande}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.notes}"></c:out>
                            </td>
                            <td>
                                <c:out value="${commande.client.nom}"></c:out>
                            </td>
                            <td class="action">
                                <a href="<c:url value="/SupprimerCommande">
                                    <c:param name="idCommande" value="${ commande.id }" />
                                    </c:url>">
                                    <i class="las la-trash"></i>
                                </a>
                                <a href="<c:url value="/ModifierCommande">
                                    <c:param name="idCommande" value="${ commande.id }" />
                                    </c:url>">
                                    <i class="las la-edit"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </div>

        </body>

        </html>