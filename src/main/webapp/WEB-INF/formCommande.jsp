<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 


			<fieldset class="border rounded border-info ps-4">
				<legend class="float-none w-auto px-3"> Informations commande</legend>
				<div id="flexchamp">

					<div class="champ my-2">
						<label class="me-2" for="label">Label</label>
						<input class="me-2" type="text" name="label" id="label" value="<c:out value="
							${commande.label}" />"
						size="20" maxlength="200">
						<span class="erreur"> ${erreurs['label']}</span>
					</div>

					<div class="champ my-2">
						<label class="me-2" for="tjmHT"> tjmHT <span class="requis"></span> </label>
						<input class="me-2" type="number" name="tjmht" id="tjmht" value="<c:out value ="
							${commande.tjmHT}" />">
						<span class="erreur"> ${erreurs['tjmHT']}</span>
					</div>

					<div class="champ my-2">
						<label class="me-2" for="dureejours"> durï¿½eJours <span class="requis"></span></label>
						<input class="me-2" type="number" name="dureejours" id="dureejours" value="<c:out value ="
							${commande.dureeJours}" />">
						<span class="erreur"> ${erreurs['dureejours']}</span>
					</div>


					<div class="champ my-2">
						<label class="me-2" for="tva">TVA</label>
						<input class="me-2" type="number" name="tva" id="tva" value="<c:out value="
							${commande.TVA}" />">
						<span class="erreur"> ${erreurs['tva']}</span>
					</div>

					<div class="champ my-2">
						<label class="me-2" for="statut"> Statut <span class="requis"></span> </label>
						<select name="statut" id="statut"> 
			                <c:forEach items = "${stat}" var="statut" > 
			                		<option value="${statut}" ${statut == commande.statut ? 'selected':'' }><c:out value ="${statut}"/></option>
			                </c:forEach>
			    		 </select>
						<span class="erreur"> ${erreurs['statut']}</span>
					</div>

					<div class="champ my-2">
						<label class="me-2" for="typecommande">Type de Commande<span class="requis"></span></label>
			            <select name="typecommande" id="typecommande"> 
			                <c:forEach items = "${types}" var="type" > 
			                		<option value="${type}" ${type == commande.typeCommande ? 'selected':'' }><c:out value ="${type}"/></option>
			                </c:forEach>
			    		 </select>
						<span class="erreur"> ${erreurs['typecommande']}</span>
					</div>

					<div class="champ my-2">
						<label class="me-2" for="notes">Notes</label>
						<input class="me-2" type="text" name="notes" id="notes" value="<c:out value="
							${commande.notes}" />">
						<span class="erreur"> ${erreurs['notes']}</span>
					</div>


					<div class="champ my-2">
						<label class="me-2" for="clients"> Clients <span class="requis"></span></label>
						<select name="clients" id="clients">
							<option value="">--Please choose an option--</option>
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
			<div id="bouttons">
				<input class="btn btn-primary me-2" type="submit" value="Valider" />
				<input class="btn btn-danger" type="reset" value="Remettre à zéro" /> <br />
			</div>

        