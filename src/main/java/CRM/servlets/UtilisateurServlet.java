package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.Dao.UtilisateursDao;
import CRM.forms.UtilisateurForm;
import CRM.utils.Authentification;
import CRM.utils.Tools;


@WebServlet("/Utilisateur")
public class UtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateursDao utilisateurDao;


    public UtilisateurServlet() {
        super();
        this.utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();

    }



	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setCharacterEncoding("UTF-8");	
	
		try {
			JsonObject data = Tools.getJsonData(request);
			
			String email = Authentification.isAuthentificated(request);
			data.addProperty("email", email);

			
			
			UtilisateurForm form = new UtilisateurForm(utilisateurDao);
			
			form.saveUtilisateur(data, UtilisateurForm.MODIFICATION);
			
			response.setStatus(form.getStatus());
			response.getWriter().write(form.getErreur());
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500); // Internal Server Error
			response.getWriter().write("Erreur: Problème serveur");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		try {
			String email = request.getParameter("email");
			String json;
			if (email != null) {
				json = new Gson().toJson(utilisateurDao.trouver(email));
			}
			else {
				json = new Gson().toJson(utilisateurDao.lister());
			}
			response.setContentType("application/json");
			response.getWriter().write(json);
		} catch (NumberFormatException e) {
			response.setStatus(400); // Bad request
			response.getWriter().write("Erreur: Le format du paramètre n'est pas bon...");
		} catch (DaoException e) {
			response.setStatus(404); // Not found
			response.getWriter().write("Erreur: DAO");			
		} catch (Exception e) {
			response.setStatus(500); // Internal Server Error
			response.getWriter().write("Erreur: Problème serveur");
		}
	}

}
