package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import CRM.services.ServiceException;
import CRM.services.ServiceModification;
import CRM.services.ServiceProduit;
import CRM.services.ServiceUtilisateur;


@WebServlet("/Produit")
public class ProduitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idProduit = request.getParameter("idProduit");
			if(idProduit != null) {
				Long id = Long.parseLong(idProduit);
				if(id > 0) {
					responseContent = new ServiceProduit().trouver(id);
					responseContentType = "application/json";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idLivre doit être strictement supérieur à 0.";
				}
			} else {
				responseContent = new ServiceProduit().lister();
				responseContentType = "application/json";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idProduit n'est pas bon.";
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
				
				Long idObjet = new ServiceProduit().ajouter(data);
				
				Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);	
				new ServiceModification().ajouter(idUtilisateur,idObjet,"Produit","ajouté");
				
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
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			new ServiceProduit().modifier(data);
			
			Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);
			Long idObjet = data.get("id").getAsLong();			
			new ServiceModification().ajouter(idUtilisateur,idObjet,"Produit","modifié");
			
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
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idProduit = request.getParameter("idProduit");
			if(idProduit != null) {
				Long id = Long.parseLong(idProduit);
				if(id > 0) {
					new ServiceProduit().supprimer(id);
					responseContent = "Suppression produit OK.";
					
					Long idUtilisateur = new ServiceUtilisateur().getIdUtilisateurActuel(request);
					Long idObjet =Long.parseLong(request.getParameter("idProduit"));			
					new ServiceModification().ajouter(idUtilisateur,idObjet,"Produit","supprimé");
					
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idProduit doit être strictement supérieur à 0.";
				}
			} else {
				responseStatus = 400;
				responseContent = "Erreur : Le paramètre idProduit est obligatoire.";
			}
		} catch(ServiceException e) {
			responseStatus = 400;
			responseContent = "Erreur : " +e.getMessage();
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idProduit n'est pas bon.";
		} catch(Exception e) {
			e.printStackTrace();
			responseStatus = 500;
			responseContent = "Erreur : Erreur serveur.";
		}
		
		ServletTools.sendResponse(response, responseStatus, responseContentType, responseContent);
	}
	
	
	
}


