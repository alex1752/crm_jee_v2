package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.DaoFactory;
import CRM.Dao.UtilisateursDao;
import CRM.forms.UtilisateurForm;
import CRM.model.Utilisateurs;


@WebServlet("/AjouterUtilisateur")
public class AjouterUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private UtilisateursDao utilisateurDao;
	
    public AjouterUtilisateur() {
        super();
        utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterUtilisateur.jsp").forward(request, response);	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurForm form = new UtilisateurForm(utilisateurDao);	
		Utilisateurs utilisateur = form.saveUtilisateur(request, UtilisateurForm.CREATION);
		
		if (form.getErreurs().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ListeUtilisateur");
		}
		else {
			request.setAttribute("utilisateur", utilisateur);
			request.setAttribute("form", form);
			this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterUtilisateur.jsp").forward(request, response);				
		}
	}

}
