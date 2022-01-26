<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




		<body>
			<div class="container mt-3">

				<fieldset class="border rounded border-info ps-4">
					<legend class="float-none w-auto px-3"> Informations utilisateur</legend>

					<input type="hidden" id="idUtilisateur" name="idUtilisateur" value="<c:out value=" ${utilisateur.id}" />"/>

					<label class="me-2" for="loginUtilisateur">Login<span class="requis">*</span></label>
					<input class="me-2" type="text" id="loginUtilisateur" name="loginUtilisateur" value="<c:out value="${utilisateur.login}" />"size="50" maxlength="50"/>
					<span class="erreur">${form.erreurs['loginUtilisateur']}</span>
					<br />


					<label class="me-2" for="motDePasseUtilisateur">Mot de Passe<span class="requis">*</span></label>
					<input class="me-2" type="password" id="motDePasseUtilisateur" name="motDePasseUtilisateur" size="50" maxlength="50" />
					<span class="erreur">${form.erreurs['motDePasseUtilisateur']}</span>
					<br />

					<label class="me-2" for="emailUtilisateur">Adresse email<span class="requis">*</label>
					<input class="me-2" type="text" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${utilisateur.email }" />"size="50" maxlength="100"/>
					<span class="erreur">${form.erreurs['emailUtilisateur']}</span>
					<br/>
					<br/>
				</fieldset>

				
				<p class="info">${form.resultat }</p>

				<input class="btn btn-primary me-2" type="submit" value="Valider le changement" />
				<input class="btn btn-danger" type="reset" value="Remettre à zéro" /><br />

			</div>
		</body>
		