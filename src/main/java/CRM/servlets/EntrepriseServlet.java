package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import CRM.services.ServiceClients;
import CRM.services.ServiceEntreprise;
import CRM.services.ServiceException;
import CRM.services.ServiceModification;
import CRM.services.ServiceUtilisateur;


@WebServlet("/Entreprise")
public class EntrepriseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
//Ajouter
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			Long idObjet = new ServiceEntreprise().ajouter(data);
			
			Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);		
			new ServiceModification().ajouter(idUtilisateur,idObjet,"Entreprise","ajouté");

			
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des données n'est pas bon, veuillez utiliser du JSON.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
	
//Lister
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		String nom = request.getParameter("nom");
		
		try {
			String idEntreprise = request.getParameter("idEntreprise");
			if(idEntreprise != null) {
				Long id = Long.parseLong(idEntreprise);
				if(id > 0) {
					responseContent = new ServiceEntreprise().trouver(id);
					responseContentType = "application/json";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idEntreprise doit être strictement supérieur à 0.";
				}
			} else {
				responseContent = new ServiceEntreprise().lister();
				responseContentType = "application/json";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idEntreprise n'est pas bon.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
		
//Modifier
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			new ServiceEntreprise().modifier(data);
			
			Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);
			Long idObjet = data.get("idEntreprise").getAsLong();			
			new ServiceModification().ajouter(idUtilisateur,idObjet,"Entreprise","modifié");
			
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des données n'est pas bon, veuillez utiliser du JSON.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}

	
//Supprimer

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idEntreprise = request.getParameter("idEntreprise");
			if(idEntreprise != null) {
				Long id = Long.parseLong(idEntreprise);
				if(id > 0) {
					new ServiceEntreprise().supprimer(id);
					responseContent = "Suppression entreprise OK.";
					
					Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);
					Long idObjet =Long.parseLong(request.getParameter("idEntreprise"));			
					new ServiceModification().ajouter(idUtilisateur,idObjet,"Entreprise","supprimé");
					
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idEntreprise doit être strictement supérieur à 0.";
				}
			} else {
				responseStatus = 400;
				responseContent = "Erreur : Le paramètre idEntreprise est obligatoire.";
			}
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idEntreprise n'est pas bon.";
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
}
