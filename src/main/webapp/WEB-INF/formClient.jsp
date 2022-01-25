<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<fieldset>
    <legend>Information Auteur</legend>

     <div class="creation">
         <label for="nom">Nom : </label>
         <input name="nom" id="nom" value="<c:out value="${client.nom}"/>" type="text" required/> <br /> 
    	<span class="erreurs">${form.erreurs['nom']}</span>
    </div>

    <div class="creation">
        <label for="prenom">Prenom :</label>
        <input name="prenom" type="text" id="prenom" value="<c:out value="${client.prenom}"/>"/> <br />
    	<span class="erreurs">${form.erreurs['prenom']}</span>
    </div>

    <div class="creation">
        <label for="telephone">Numéro de téléphone :</label>
        <input name="telephone" id="telephone" value="<c:out value="${client.telephone}"/>" type ="telephone" required/> <br/>
        <span class="erreurs">${form.erreurs['telephone']}</span>
    </div>

    <div class="creation">
        <label for="email">Adresse Email : </label>
        <input name="email" type="email" value="<c:out value="${client.email}"/>" id="mail"/><br/>
        <span class="erreurs">${form.erreurs['email']}</span>
    </div>

	<div class="creation">
        <label for="entreprise">Entreprise :</label>
        <input name="entreprise" type="text" id="entreprise" value="<c:out value="${client.entreprise}"/>"/> <br />
    	<span class="erreurs">${form.erreurs['entreprise']}</span>
    </div>
    
   <div class="creation">
   		<p>Client Actif :</p>
        <label for="actif">Oui</label>
        <input name="actif" type="radio" id="actif1" value="<c:out value="${client.actif}"/>"${auteur.id == livre.auteur.id ? 'checked' : ''}/> <br />
    	<span class="erreurs">${form.erreurs['actif']}</span>
    	 <label for="actif">Non</label>
        <input name="actif" type="radio" id="actif2" value="<c:out value="${client.actif}"/>"${auteur.id == livre.auteur.id ? 'checked' : ''}/> <br />
    	<span class="erreurs">${form.erreurs['actif']}</span>
    </div>
    
    <div class="creation">
        <label for="notes">Notes:</label>
        <input name="notes" type="text" id="notes" value="<c:out value="${client.notes}"/>"/> <br />
    	<span class="erreurs">${form.erreurs['notes']}</span>
    </div>

    <div class="creation">
        <br/>
        <p class="info">${form.resultat}</p>
        <input type="submit" value="Valider" />  <input type="reset" value="Remise à zéro">
    </div>
</fieldset>