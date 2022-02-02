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
import CRM.forms.ClientForm;
import CRM.forms.UtilisateurForm;
import CRM.model.Utilisateurs;
import CRM.utils.Tools;



@WebServlet("/CreerUtilisateur")
public class CreerUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UtilisateursDao utilisateurDao;

	
    public CreerUtilisateurServlet() {
        super();
        this.utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		try {			
			JsonObject data = Tools.getJsonData(request);
			
			UtilisateurForm form = new UtilisateurForm(utilisateurDao);
			form.saveUtilisateur(data, UtilisateurForm.CREATION);
			
			response.setStatus(form.getStatus());
			response.getWriter().write(form.getErreur());
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500); // Internal Server Error
			response.getWriter().write("Erreur: Problème serveur");
		}
	}


	


}
