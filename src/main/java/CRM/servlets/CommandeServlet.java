package CRM.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.model.Commandes;
import CRM.services.ServiceCommande;
import CRM.services.ServiceException;
import CRM.utils.Tools;

@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;

		try {
			String idCommande = request.getParameter("idCommande");
			String idClient = request.getParameter("idClient");
			
			if(idClient != null) {
				
				Long idClientParse = Long.parseLong(idClient);
				
				if(idClientParse>0) {
					responseContent = new ServiceCommande().trouverCommandesClient(idClientParse);
					responseContentType = "application/json";
				}else {
					responseStatus = 400;
					responseContent = "Erreur : L'idClient doit être strictement supérieur à 0.";
				}
				
			}else if(idCommande != null) {
				
				Long idCommandeParse = Long.parseLong(idCommande);
				
				if(idCommandeParse>0) {
					responseContent = new ServiceCommande().trouver(idCommandeParse);
					responseContentType = "application/json";
				} else {
					responseStatus = 400;
					responseContent = "Erreur : L'idCommande doit être strictement supérieur à 0.";
				}
							
			}else {
				responseContent = new ServiceCommande().lister();
				responseContentType= "application/json";
			}
			
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idCommande n'est pas bon.";
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
			
			new ServiceCommande().ajouter(data);
			
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
			
			new ServiceCommande().modifier(data);
			
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
		String responseContent="ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idCommande=request.getParameter("idCommande");
		
			if(idCommande != null) {
				Long idCommandeParse = Long.parseLong(idCommande);
				if(idCommandeParse>0) {
					new ServiceCommande().supprimer(idCommandeParse);
					responseContent = "Suppression de la commande OK.";
				}else {
					responseStatus = 400;
					responseContent = "Erreur : L'idCommande doit être strictement supérieur à 0.";
				} 
			}else {
				responseStatus = 400;
				responseContent = "Erreur : Le paramètre idCommande est obligatoire.";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre idCommande n'est pas bon.";
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
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase("PATCH")){
			
			String responseContent="Ok", responseContentType = "text";
			int responseStatus = 200;
			
			try {
				String action = request.getParameter("action");
				if(action != null && (action.equals("addProduit") || action.equals("removeProduit"))) {
					String idCommande = request.getParameter("idCommande");
					String idProduit = request.getParameter("idProduit");
					if(idProduit != null && idCommande != null) {
						
						Long idCommandeParse = Long.parseLong(idCommande);
						Long idProduitParse = Long.parseLong(idProduit);
						
						if(idProduitParse > 0 && idCommandeParse > 0) {
							
							if(action.equals("addGenre")) {
								new ServiceCommande().addProduit(idCommandeParse, idProduitParse);
								responseContent = "Le produit a été ajouté à la commande.";
							} else {
								new ServiceCommande().removeProduit(idProduitParse, idCommandeParse);
								responseContent = "Le produit a été supprimé de la commande.";
							}
						} else {
							responseStatus = 400;
							responseContent = "Erreur : L'idCommande et l'idProduit doivent être strictement supérieur à 0.";
						}
					} else {
						responseStatus = 400;
						responseContent = "Erreur : Les paramètres idProduit et idCommande sont obligatoires.";
					}
				} else {
					responseStatus = 400;
					responseContent = "Erreur : Le paramètre action est obligatoire. 2 valeurs possibles : addProduit et removeProduit";
				}
			} catch(NumberFormatException e) {
				responseStatus = 400;
				responseContent = "Erreur : Le format du paramètre idCommande ou idProduit n'est pas bon.";
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
