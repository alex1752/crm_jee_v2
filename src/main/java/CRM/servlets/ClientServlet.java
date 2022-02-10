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
			
			new ServiceClients().ajouter(data);

			
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
			String idAuteur = request.getParameter("idAuteur");
			if(idAuteur != null) {
				Long id = Long.parseLong(idAuteur);
				if(id > 0) {
					new ServiceClients().supprimer(id);
					responseContent = "Suppression auteur OK.";
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
