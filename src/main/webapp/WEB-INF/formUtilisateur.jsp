<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<head>
			<meta charset="UTF-8">
			<title>Document</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
				integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
				crossorigin="anonymous">
		</head>

<<<<<<< HEAD
	        <label for="loginUtilisateur">Login<span class="requis">*</span></label>
              	<input type="text" id="loginUtilisateur" name="loginUtilisateur" value="<c:out value="${utilisateur.login }"/>"size="30" maxlength="30"/>
              	<span class="erreur">${form.erreurs['loginUtilisateur']}</span>
              	<br/>
              	
              	
      		<label for="motDePasseUtilisateur">Mot de Passe<span class="requis">*</span></label>
             	<input type="password" id="motDePasseUtilisateur" name="motDePasseUtilisateur" size="30" maxlength="30"/>
             	<span class="erreur">${form.erreurs['motDePasseUtilisateur']}</span>
             	<br/>
             	
             <label for="emailUtilisateur">Adresse email<span class="requis">*</label>
             	<input type="text" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="${utilisateur.email }"/>"size="30" maxlength="100"/>
             	<span class="erreur">${form.erreurs['emailUtilisateur']}</span>
             	<br/>
              	
</fieldset>
<p class="info">${form.resultat }</p>
=======
		<body>
			<div class="container mt-3">

				<fieldset class="border rounded border-info ps-4">
					<legend class="float-none w-auto px-3"> Informations utilisateur</legend>

					<input type="hidden" id="idUtilisateur" name="idUtilisateur" value="<c:out value=" ${utilisateur.id
						}" />"/>

					<label class="me-2" for="loginUtilisateur">Login<span class="requis">*</span></label>
					<input class="me-2" type="text" id="nomAuteur" name="nomAuteur" value="<c:out value=" ${auteur.nom
						}" />"size="30" maxlength="30"/>
					<span class="erreur">${form.erreurs['loginUtilisateur']}</span>
					<br />
>>>>>>> niceHTML


					<label class="me-2" for="motDePasseUtilisateur">Mot de Passe<span class="requis">*</span></label>
					<input class="me-2" type="password" id="motDePasseUtilisateur" name="motDePasseUtilisateur" size="30"
						maxlength="30" />
					<span class="erreur">${form.erreurs['motDePasseUtilisateur']}</span>
					<br />

<<<<<<< HEAD
    
    
=======
					<label class="me-2" for="emailUtilisateur">Adresse email<span class="requis">*</label>
					<input class="me-2" type="text" id="emailUtilisateur" name="emailUtilisateur" value="<c:out value="
						${utilisateur.email }" />"size="100" maxlength="100"/>
					<span class="erreur">${form.erreurs['emailUtilisateur']}</span>
					<br />

				</fieldset>
				
				<p class="info">${form.resultat }</p>

				<input class="btn btn-primary me-2" type="submit" value="Valider le changement" />
				<input class="btn btn-danger" type="reset" value="Remettre à zéro" /><br />

			</div>
		</body>
>>>>>>> niceHTML
