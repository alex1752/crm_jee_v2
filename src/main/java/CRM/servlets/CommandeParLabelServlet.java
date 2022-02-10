package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import CRM.services.ServiceCommande;
import CRM.services.ServiceException;


@WebServlet("/commandeParLabel")
public class CommandeParLabelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String responseContent="Ok", responseContentType = "text";
		int responseStatus = 200;
		
		try {
			
			JsonObject data = ServletTools.getJsonFromBuffer(request);
			
			responseContent = new ServiceCommande().listerParLabel(data);
			responseContentType = "application/json";

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

}
