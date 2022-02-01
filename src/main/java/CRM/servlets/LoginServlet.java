package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import CRM.Dao.DaoFactory;
import CRM.Dao.UtilisateursDao;
import CRM.model.Utilisateurs;
import CRM.utils.Authentification;
import CRM.utils.TokenJWT;
import CRM.utils.Tools;





@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtilisateursDao utilisateurDao;

	
    public LoginServlet() {
        super();
        this.utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String erreur = "ok";
		int status = 200;
		
		try {
			JsonObject data = Tools.getJsonData(request);


			String email = null;
			if (data.get("email") != null) {
				email = data.get("email").getAsString();
			}
			else {
				erreur = "L'email est obligatoire"; 
			}
			
			String motDePasse = null;
			if (data.get("motDePasse") != null) {
				motDePasse = data.get("motDePasse").getAsString();
			}
			else {
				erreur = "Le mot de passe est obligatoire"; 
			}
			
			
			if (erreur.equals("ok")) {
				Utilisateurs utilisateur = utilisateurDao.trouver(email,Authentification.hashPass(motDePasse));
				if (utilisateur != null) {
					long ttlMinutes = 30;
					String token = TokenJWT.generateJWT(email, ttlMinutes);

					JsonObject userJson = new JsonObject();
					userJson.addProperty("login", utilisateur.getLogin());
					userJson.addProperty("email", utilisateur.getEmail());
					
					JsonObject dataReturn = new JsonObject();
					dataReturn.addProperty("token", token); // addProperty : pour un string 
					dataReturn.add("utilisateur", userJson); // add : pour un objet json
					
					response.setContentType("application/json");
					
					erreur = dataReturn.toString();
				}
				else {
					status = 400; // bad request
					erreur = "Les identifiants ne sont pas correct...";
					
				}
			}
			else {
				status = 400; // bad request
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500); // Internal Server Error
			response.getWriter().write("Erreur: Problème serveur");
		}
		response.setStatus(status); 
		response.getWriter().write(erreur);
	}

}
