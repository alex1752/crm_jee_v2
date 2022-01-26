<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>

        <head>
            <meta charset="UTF-8">
            <title>Document</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                crossorigin="anonymous">
        </head>

        <body>

            <div class="container mt-3">

                <fieldset class="border rounded border-info ps-4">

                    <legend class="float-none w-auto px-3">Information Auteur</legend>

<<<<<<< HEAD
	<div class="creation">
        <label for="entreprise">Entreprise :</label>
        <input name="entreprise" type="text" id="entreprise" value="<c:out value="${client.entreprise}"/>"/> <br />
    	<span class="erreurs">${form.erreurs['entreprise']}</span>
    </div>
    
   <div class="creation">
   		<p>	Client Actif :
        <label for="actif">Oui</label>
        <input name="actif" type="radio" id="actif1" value="<c:out value="${client.actif}"/>"/> 
    	<span class="erreurs">${form.erreurs['actif1']}</span>
    	 <label for="actif">Non</label>
        <input name="actif" type="radio" id="actif2" value="<c:out value="${client.actif}"/>"/> <br />
    	<span class="erreurs">${form.erreurs['actif2']}</span></p>
    </div>
    
    <div class="creation">
        <label for="notes">Notes:</label>
        <textarea id="notes" name="notes" rows="5" cols="33" placeholder="votre note..."></textarea>
<%--         <input name="notes" type="text" id="notes" value="<c:out value="${client.notes}"/>"/> <br /> --%>
    	<span class="erreurs">${form.erreurs['notes']}</span>
    </div>
=======
                    <div class="creation my-2">
                        <label class="me-2" for="nom">Nom : </label>
                        <input class="me-2" name="nom" id="nom" value="<c:out value=" ${client.nom}" />" type="text"
                        required/>
                        <br />
                        <span class="erreurs">${form.erreurs['nom']}</span>
                    </div>
>>>>>>> niceHTML

                    <div class="creation my-2">
                        <label class="me-2" for="prenom">Prenom :</label>
                        <input class="me-2" name="prenom" type="text" id="prenom" value="<c:out value="
                            ${client.prenom}" />"/>
                        <br />
                        <span class="erreurs">${form.erreurs['prenom']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="telephone">Num�ro de t�l�phone :</label>
                        <input class="me-2" name="telephone" id="telephone" value="<c:out value="
                            ${client.telephone}" />" type
                        ="telephone" required/> <br />
                        <span class="erreurs">${form.erreurs['telephone']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="email">Adresse Email : </label>
                        <input class="me-2" name="email" type="email" value="<c:out value=" ${client.email}" />"
                        id="mail"/><br />
                        <span class="erreurs">${form.erreurs['email']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="entreprise">Entreprise :</label>
                        <input class="me-2" name="entreprise" type="text" id="entreprise" value="<c:out value="
                            ${client.entreprise}" />"/> <br />
                        <span class="erreurs">${form.erreurs['entreprise']}</span>
                    </div>

                    <div class="creation my-2">
                        <p>Client Actif :</p>
                        <label for="actif">Oui</label>
                        <input class="me-2" name="actif" type="radio" id="actif1" value="<c:out value="
                            ${client.actif}" />"${auteur.id == livre.auteur.id ? 'checked' : ''}/> <br />
                        <span class="erreurs">${form.erreurs['actif']}</span>
                        <label for="actif">Non</label>
                        <input name="actif" type="radio" id="actif2" value="<c:out value="
                            ${client.actif}" />"${auteur.id == livre.auteur.id ? 'checked' : ''}/> <br />
                        <span class="erreurs">${form.erreurs['actif']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="notes">Notes:</label>
                        <input class="me-2" name="notes" type="text" id="notes" value="<c:out value=" ${client.notes}" />"/> <br />
                        <span class="erreurs">${form.erreurs['notes']}</span>
                    </div>

                    <div class="creation my-2">
                        <br />
                        <p class="info">${form.resultat}</p>
                        <input class="btn btn-primary me-2" type="submit" value="Valider" />
                        <input class="btn btn-danger" type="reset" value="Remise � z�ro">
                    </div>

                </fieldset>

            </div>

        </body>