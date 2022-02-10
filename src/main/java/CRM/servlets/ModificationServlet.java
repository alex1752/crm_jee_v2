package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.services.ServiceCommande;
import CRM.services.ServiceException;
import CRM.services.ServiceModification;


@WebServlet("/modification")
public class ModificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			String idUtilisateur = request.getParameter("idUtilisateur");
			
			if(idUtilisateur != null) {
				Long idUtilisateurParse = Long.parseLong(idUtilisateur);
				
				if(idUtilisateurParse>0) {
					responseContent = new ServiceModification().listerParUtilisateur(idUtilisateurParse);
					responseContentType = "application/json";
				}else {
					responseStatus = 400;
					responseContent = "Erreur : L'idUtilisateur doit être strictement supérieur à 0.";
				}
			} else {
				responseContent = new ServiceModification().lister();
				responseContentType= "application/json";
			}
		} catch(NumberFormatException e) {
			responseStatus = 400;
			responseContent = "Erreur : Le format du paramètre n'est pas bon.";
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

}
