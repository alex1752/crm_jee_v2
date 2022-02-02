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
import CRM.forms.UtilisateurForm;
import CRM.utils.Authentification;
import CRM.utils.Tools;


@WebServlet("/ModifierPasswordUtilisateur")
public class ModifierPasswordUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateursDao utilisateurDao;


    public ModifierPasswordUtilisateur() {
        super();
        this.utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");	
    	
    		try {
    			JsonObject data = Tools.getJsonData(request);
    			
    			String email = Authentification.isAuthentificated(request);
    			data.addProperty("email", email);

    			System.out.println(data);
    			
    			UtilisateurForm form = new UtilisateurForm(utilisateurDao);
    			
    			form.changePassword(data);
    			
    			response.setStatus(form.getStatus());
    			response.getWriter().write(form.getErreur());
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    			response.setStatus(500); // Internal Server Error
    			response.getWriter().write("Erreur: Problème serveur");
    		}
    	}

}
