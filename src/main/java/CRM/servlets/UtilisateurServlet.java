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

import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.Dao.UtilisateursDao;
import CRM.forms.UtilisateurForm;
import CRM.services.ServiceException;
import CRM.services.ServiceUtilisateur;
import CRM.utils.Authentification;
import CRM.utils.Tools;




@WebServlet("/Utilisateur")
public class UtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateursDao utilisateurDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idUtilisateur = request.getParameter("idUtilisateur");
			if(idUtilisateur != null) {
				Long id = Long.parseLong(idUtilisateur);
				if(id > 0) {
					responseContent = new ServiceUtilisateur().trouver(id);
					responseContentType = "application/json";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idUtilisateur doit être strictement supérieur à 0.";
				}
			} else {
				responseContent = new ServiceUtilisateur().lister();
				responseContentType = "application/json";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du param�tre idGenre n'est pas bon.";
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
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			// email imposé --> utilisateur correspondant au login
			String email = Authentification.isAuthentificated(request);
			data.addProperty("email", email);

			new ServiceUtilisateur().ajouter(data);
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des données n'est pas bon, veuillez utiliser du JSON.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			// email imposé --> utilisateur correspondant au login
			String email = Authentification.isAuthentificated(request);
			data.addProperty("email", email);
			
			// MODIFICATION LOGIN
			new ServiceUtilisateur().modifierLogin(data);
		} catch(JsonSyntaxException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format des données n'est pas bon, veuillez utiliser du JSON.";
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase("PATCH")){
			
			String responseContent="Ok", responseContentType = "text";
			int responseStatus = 200;
			
			try {
				JsonObject data = ServletTools.getJsonFromBuffer(request);
				
				// email imposé --> utilisateur correspondant au login
				String email = Authentification.isAuthentificated(request);
				data.addProperty("email", email);
				
				// MODIFICATION MOT DE PASSE
				new ServiceUtilisateur().modifierMotDePasse(data);
			} catch(NumberFormatException e) {
				responseStatus = 400;
				responseContent = "Erreur : Le format du param�tre idLivre ou idGenre n'est pas bon.";
			} catch(ServiceException e) {
				responseStatus = 400;
				responseContent = "Erreur : " +e.getMessage();
			} catch(Exception e) {
				e.printStackTrace();
				responseStatus = 500;
				responseContent = "Erreur : Erreur serveur.";
			}
			
			ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);	
			
        } else {
            super.service(request, response);
        }
	}
	

}
