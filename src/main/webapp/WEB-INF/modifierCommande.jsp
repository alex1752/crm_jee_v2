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
	<form method="post" action="<c:url value="/modifierCommande"><c:param name="idCommande" value="${ commande.id }" /></c:url>">
		<fieldset>
			    <legend> Informations commande</legend>
			    <div id = "flexchamp">
			
			        <div class = "champ">
			            <label for="label">Label</label>
			            <input type="text" name="label" id="label" value="<c:out value="${commandes.label}"/>" size="200" maxlength="200">
			            <span class="erreur"> ${erreurs['label']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="tjmHT"> tjmHT <span class="requis"></span> </label>
			            <input type="number" name="tjmht" id= "tjmht" value="<c:out value ="${commandes.tjmHT}"/>">
			            <span class="erreur"> ${erreurs['tjmHT']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="dureejours"> duréeJours <span class="requis"></span></label>
			            <input type="number" name="dureejours" id= "dureejours" value="<c:out value ="${commandes.dureejours}"/>">
			            <span class="erreur"> ${erreurs['dureejours']}</span>
			        </div>
			        
			        
			          <div class = "champ">
			            <label for="tva">TVA</label>
			            <input type="number" name="tva" id="tva" value="<c:out value="${commandes.TVA}"/>">
			            <span class="erreur"> ${erreurs['tva']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="statut"> Statut <span class="requis"></span> </label>
			            <input type="text" name="statut" id= "statut" value="<c:out value ="${commande.statut}"/>" size="200" maxlength="200">
			            <span class="erreur"> ${erreurs['statut']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="typecommande">Type de Commande<span class="requis"></span></label>
			            <input type="text" name="typecommande" id="typecommande" value="<c:out value ="${commandes.typeCommande}"/>" size="20" maxlength="20">
			            <span class="erreur"> ${erreurs['typecommande']}</span>
			        </div>
			        
			          <div class = "champ">
			            <label for="notes">Notes</label>
			            <input type="text" name="notes" id="notes" value="<c:out value="${commande.notes}"/>">
			            <span class="erreur"> ${erreurs['notes']}</span>
			        </div>
	 
			        
			        <div class = "champ">
			            <label for = "clients"> Clients <span class="requis"></span></label>
			            <!-- -->
			            <select name="clients" id="clients"> 
			              <option value="">--Please choose an option--</option>
			                <c:forEach items = "${ clients }" var="client" > 
			                	<c:if test= "${commandes.clients.id != clients.id }">
			                		<option value="${clients.id}"><c:out value ="${clients.prenom}"/><c:out value ="${clients.nom}"/></option>
			                	</c:if>
			                </c:forEach>
			                <span class="erreur"> ${erreurs['clients']}</span>
			    		 </select>
			        </div>
		        </div> 
			</fieldset>
		</form>
</body>
</html>