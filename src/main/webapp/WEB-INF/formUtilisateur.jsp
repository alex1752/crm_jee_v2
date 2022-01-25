<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fieldset>
	<legend> Informations utilisateur</legend>
			
            <input type="hidden" id="idUtilisateur" name="idUtilisateur" value="<c:out value="${utilisateur.id }"/>"/>

	        <label for="loginUtilisateur">Login<span class="requis">*</span></label>
              	<input type="text" id="nomAuteur" name="nomAuteur" value="<c:out value="${auteur.nom }"/>"size="30" maxlength="30"/>
              	<span class="erreur">${form.erreurs['loginUtilisateur']}</span>
              	<br/>
              	
              	
      		<label for="motDePasseUtilisateur">Mot de Passe<span class="requis">*</span></label>
             	<input type="password" id="motDePasseUtilisateur" name="motDePasseUtilisateur" size="30" maxlength="30"/>
             	<span class="erreur">${form.erreurs['motDePasseUtilisateur']}</span>
             	<br/>
             	
             <label for="emailUtilisateur">Adresse email<span class="requis">*</label>
             	<input type="text" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${utilisateur.email }"/>"size="100" maxlength="100"/>
             	<span class="erreur">${form.erreurs['emailUtilisateur']}</span>
             	<br/>
              	
</fieldset>
<p class="info">${form.resultat }</p>


<input type="submit" value="Valider le changement"/>
<input type="reset" value="Remettre à zéro"/><br/>

    
    