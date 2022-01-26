<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


        <body>

            <div class="container mt-3">

                <fieldset class="border rounded border-info ps-4">

                    <legend class="float-none w-auto px-3">Information Auteur</legend>

                    <div class="creation my-2">
                        <label class="me-2" for="nom">Nom : </label>
                        <input class="me-2" name="nom" id="nom" value="<c:out value="${client.nom}" />" type="text" required/>
                        <br />
                        <span class="erreurs">${form.erreurs['nom']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="prenom">Prenom :</label>
                        <input class="me-2" name="prenom" type="text" id="prenom" value="<c:out value="${client.prenom}" />"/>
                        <br />
                        <span class="erreurs">${form.erreurs['prenom']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="telephone">Numéro de téléphone :</label>
                        <input class="me-2" name="telephone" id="telephone" value="<c:out value="${client.telephone}"/>" type="telephone" required/> <br />
                        <span class="erreurs">${form.erreurs['telephone']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="email">Adresse Email : </label>
                        <input class="me-2" name="email" type="email" value="<c:out value="${client.email}"/>"
                        id="mail"/><br />
                        <span class="erreurs">${form.erreurs['email']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="entreprise">Entreprise :</label>
                        <input class="me-2" name="entreprise" type="text" id="entreprise" value="<c:out value="${client.entreprise}"/>"/><br />              
                        <span class="erreurs">${form.erreurs['entreprise']}</span>
                    </div>

                    <div class="creation my-2">
                        <p>Client Actif :</p>
                        <label for="actif">Oui</label>
        					<input class="me-2" name="actif" type="radio" id="actif" value="${client.actif}" ${client.actif == true ? 'checked':'' }/>
                        <span class="erreurs">${form.erreurs['actif']}</span>
                        <label for="actif">Non</label>
        					<input class="me-2" name="actif" type="radio" id="actif" value="${client.actif}" ${client.actif == false ? 'checked':'' }/>
                        <span class="erreurs">${form.erreurs['actif']}</span>
                    </div>

                    <div class="creation my-2">
                        <label class="me-2" for="notes">Notes:</label>
                        <textarea class="me-2" id="notes" name="notes" value="<c:out value="${client.notes}"/>" rows="5" cols="33" placeholder="votre note...">${client.notes }</textarea>
                        <span class="erreurs">${form.erreurs['notes']}</span>
                    </div>

                    <div class="creation my-2">
                        <br />
                        <p class="info">${form.resultat}</p>
                        <input class="btn btn-primary me-2" type="submit" value="Valider" />
                        <input class="btn btn-danger" type="reset" value="Remise à zéro">
                    </div>

                </fieldset>

            </div>

        </body>