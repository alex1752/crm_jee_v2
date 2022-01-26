<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>


<fieldset>
		     <legend> Informations commande</legend>
		     
			    <div id = "flexchamp">
			
			        <div class = "champ">
			            <label for="label">Label</label>
			            <input type="text" name="label" id="label" value="<c:out value="${commande.label}"/>" size="20" maxlength="200">
			            <span class="erreur"> ${erreurs['label']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="tjmHT"> tjmHT <span class="requis"></span> </label>
			            <input type="number" name="tjmht" id= "tjmht" value="<c:out value ="${commande.tjmHT}"/>">
			            <span class="erreur"> ${erreurs['tjmHT']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="dureejours"> duréeJours <span class="requis"></span></label>
			            <input type="number" name="dureejours" id= "dureejours" value="<c:out value ="${commande.dureeJours}"/>">
			            <span class="erreur"> ${erreurs['dureejours']}</span>
			        </div>
			        
			        
			          <div class = "champ">
			            <label for="tva">TVA</label>
			            <input type="number" name="tva" id="tva" value="<c:out value="${commande.TVA}"/>">
			            <span class="erreur"> ${erreurs['tva']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for="statut"> Statut <span class="requis"></span> </label>
			             <select name="statut" id="statut"> 
			                <c:forEach items = "${stat}" var="statut" > 
			                		<option value="${statut}" ${statut == commande.statut ? 'selected':'' }><c:out value ="${statut}"/></option>
			                </c:forEach>
			    		 </select>
			            <span class="erreur"> ${erreurs['statut']}</span>
			        </div>
			
			        <div class = "champ">
			            <label for = "typecommande"> Type de Commande <span class="requis"></span></label>
			            <select name="typecommande" id="typecommande"> 
			                <c:forEach items = "${types}" var="type" > 
			                		<option value="${type}" ${type == commande.typeCommande ? 'selected':'' }><c:out value ="${type}"/></option>
			                </c:forEach>
			    		 </select>
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
			                <c:forEach items = "${ clients }" var="client" > 
			                		<option value="${client.id}" ${commande.client.id == client.id ? 'selected':'' }><c:out value ="${client.nom}"/> <c:out value ="${client.prenom}"/></option>
			                </c:forEach>
			    		 </select>
			             <span class="erreur"> ${erreurs['clients']}</span>
			        </div>
	        </div> 
		</fieldset>
		<p class="info">${ resultat }</p>
        <div id = "bouttons">
            <input type="submit" value="Valider" />
		<input type="reset" value="Remettre à zéro" /> <br />
        </div>