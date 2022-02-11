package CRM.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import CRM.services.ServiceClients;
import CRM.services.ServiceException;
import CRM.services.ServiceModification;
import CRM.services.ServiceTools;
import CRM.services.ServiceUtilisateur;





@WebServlet("/Client")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

//Ajouter
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			Long idObjet = new ServiceClients().ajouter(data);

			Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);			
			new ServiceModification().ajouter(idUtilisateur,idObjet,"Client","ajouté");
			
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
			String idClient = request.getParameter("idClient");
			if(idClient != null) {
				Long id = Long.parseLong(idClient);
				if(id > 0) {
					responseContent = new ServiceClients().trouver(id);
					responseContentType = "application/json";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idClient doit être strictement supérieur à 0.";
				}
			} else if(nom !=null) {
				responseContent = new ServiceClients().listerParNom(nom);
				responseContentType = "application/json";
			} else {
				responseContent = new ServiceClients().lister();
				responseContentType = "application/json";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idClient n'est pas bon.";
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
			
			new ServiceClients().modifier(data);

			
			Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);
			Long idObjet = data.get("id").getAsLong();
			new ServiceModification().ajouter(idUtilisateur,idObjet,"Client","modifié");
			
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des donn�es n'est pas bon, veuillez utiliser du JSON.";
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
			String idClient = request.getParameter("idClient");
			if(idClient != null) {
				Long id = Long.parseLong(idClient);
				if(id > 0) {
					new ServiceClients().supprimer(id);
					responseContent = "Suppression client OK.";
					
					Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);
					Long idObjet =Long.parseLong(request.getParameter("idClient"));			
					new ServiceModification().ajouter(idUtilisateur,idObjet,"Client","supprimé");
					
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idClient doit être strictement supérieur à 0.";
				}
			} else {
				responseStatus = 400;
				responseContent = "Erreur : Le paramètre idClient est obligatoire.";
			}
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idClient n'est pas bon.";
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
	
}
